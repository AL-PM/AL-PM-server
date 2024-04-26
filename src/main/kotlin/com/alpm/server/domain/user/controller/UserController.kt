package com.alpm.server.domain.user.controller

import com.alpm.server.domain.user.dto.response.RefreshTokenResponseDto
import com.alpm.server.domain.user.dto.response.UserWithHistoryResponseDto
import com.alpm.server.domain.user.service.UserService
import com.alpm.server.global.auth.jwt.JwtAuthenticationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User", description = "User API")
@RestController
@RequestMapping("/user")
class UserController (

    private val jwtAuthenticationService: JwtAuthenticationService,

    private val userService: UserService

) {

    @Operation(summary = "Access Token 재발급")
    @GetMapping("/refresh")
    fun refreshToken(@RequestHeader("Refresh") refreshToken: String): ResponseEntity<RefreshTokenResponseDto> {
        return ResponseEntity.ok().body(jwtAuthenticationService.refreshToken(refreshToken))
    }

    @Operation(summary = "User 단일 조회")
    @GetMapping("/{id}")
    fun readUserById(@PathVariable("id") id: Long): ResponseEntity<UserWithHistoryResponseDto> {
        return ResponseEntity.ok().body(userService.readUserById(id))
    }

}