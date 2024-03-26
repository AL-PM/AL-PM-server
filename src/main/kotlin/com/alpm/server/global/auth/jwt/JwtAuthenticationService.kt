package com.alpm.server.global.auth.jwt

import com.alpm.server.domain.user.dto.RefreshTokenDto
import com.alpm.server.domain.user.service.UserService
import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import com.alpm.server.infra.redis.RedisDao
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.*

@Service
class JwtAuthenticationService (

    @Value("\${jwt.secret}")
    private var secretKey: String,

    @Value("\${jwt.token.access-expiration-time}")
    private val tokenPeriod: Long,

    @Value("\${jwt.token.refresh-expiration-time}")
    private val refreshPeriod: Long,

    private val userService: UserService,

    private val redisDao: RedisDao

) {

    @PostConstruct
    private fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun generateTokenPair(id: String, provider: String, uid: String): Pair<String, String> {
        val tokenClaims = Jwts.claims()
            .subject(id)
            .add(mapOf(Pair("provider", provider), Pair("uid", uid)))
            .build()
        val refreshClaims = Jwts.claims()
            .subject(id)
            .build()

        val accessToken = generateToken(tokenPeriod, tokenClaims)
        val refreshToken = generateToken(refreshPeriod, refreshClaims)
        redisDao.setValues(id, refreshToken, Duration.ofMillis(refreshPeriod))

        return Pair(accessToken, refreshToken)
    }

    fun refreshToken(refreshToken: String): RefreshTokenDto {
        val split = refreshToken.split(" ")
        if (split.size != 2 || split[0] != "Bearer") {
            throw CustomException(ErrorCode.INVALID_TOKEN)
        }

        val id = getId(split[1])
        if (split[1] != redisDao.getValues(id)) {
            throw CustomException(ErrorCode.INVALID_TOKEN)
        }

        val user = userService.loadUserById(id.toLong())
        val claims = Jwts.claims()
            .subject(id)
            .add(mapOf(Pair("provider", user.provider), Pair("uid", user.uid)))
            .build()

        return RefreshTokenDto(generateToken(tokenPeriod, claims))
    }

    fun generateToken(period: Long, claims: Claims): String {
        val now = Date()
        return Jwts.builder()
            .claims(claims)
            .issuedAt(now)
            .expiration(Date(now.time + period))
            .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray(Charsets.UTF_8)))
            .compact()
    }

    fun verifyToken(token: String): Boolean {
        return try {
            val user = userService.loadUserById(getId(token).toLong())

            val claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray(Charsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
            if (claims.payload["provider"] != user.provider || claims.payload["uid"] != user.uid) {
                return false
            }

            return claims.payload
                .expiration
                .after(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun getId(token: String): String {
        return try {
            Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray(Charsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .payload
                .subject
        } catch (e: Exception) {
            throw CustomException(ErrorCode.INVALID_TOKEN)
        }
    }

    fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        val id: String = getId(token)
        val user = userService.loadUserById(id.toLong())

        return UsernamePasswordAuthenticationToken(
            user,
            "",
            emptyList()
        )
    }

}