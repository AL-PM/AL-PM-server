package com.alpm.server.global.auth.oauth2

import com.alpm.server.domain.user.dao.UserRepository
import com.alpm.server.domain.user.dto.LoginResponseDto
import com.alpm.server.domain.user.dto.UserDto
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.auth.jwt.JwtAuthenticationService
import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import com.alpm.server.infra.google.GoogleOAuth2Service
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service

@Service
class OAuth2Service (

    private val googleOAuth2Service: GoogleOAuth2Service,

    private val userRepository: UserRepository,

    private val jwtAuthenticationService: JwtAuthenticationService

) {

    fun oAuth2Login(code: String, registrationId: String): LoginResponseDto {
        val accessToken = getAccessToken(code, registrationId)
        val userResourceNode = getUserResource(accessToken, registrationId)!!
        val uid = userResourceNode.get("id").asText()
        val nickname = userResourceNode.get("name").asText()

        var user = userRepository.findByUidAndProvider(uid, registrationId)
        if (user == null) {
            user = userRepository.save(
                User(
                    name = nickname,
                    uid = uid,
                    provider = registrationId
                )
            )
        }

        val tokenPair = jwtAuthenticationService.generateTokenPair(user.id!!.toString(), user.provider, user.uid)

        return LoginResponseDto(
            user = UserDto(user),
            accessToken = tokenPair.first,
            refreshToken = tokenPair.second
        )
    }

    fun oAuth2Redirect(registrationId: String): HttpHeaders {
        return when(registrationId) {
            "google" -> googleOAuth2Service.oAuth2Redirect()
            else -> throw CustomException(ErrorCode.OAUTH2_PROVIDER_ERROR)
        }
    }

    fun getAccessToken(code: String, registrationId: String): String {
        return when(registrationId) {
            "google" -> googleOAuth2Service.getAccessToken(code, registrationId)
            else -> throw CustomException(ErrorCode.OAUTH2_PROVIDER_ERROR)
        }
    }

    fun getUserResource(accessToken: String, registrationId: String): JsonNode? {
        return when(registrationId) {
            "google" -> googleOAuth2Service.getUserResource(accessToken, registrationId)
            else -> throw CustomException(ErrorCode.OAUTH2_PROVIDER_ERROR)
        }
    }

}