package com.alpm.server.domain.user.controller

import com.alpm.server.domain.user.dto.response.UserLoginResponseDto
import com.alpm.server.global.auth.oauth2.OAuth2Service
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User OAuth2", description = "User OAuth2 API")
@RestController
@RequestMapping("/oauth2")
class UserOAuth2Controller (

    private val oAuth2Service: OAuth2Service

) {

    @Operation(summary = "OAuth2 로그인 화면으로 리다이렉트")
    @GetMapping("/authorization/{registrationId}")
    fun oAuth2Redirect(
        @PathVariable("registrationId") registrationId: String
    ): ResponseEntity<HttpHeaders> {
        return ResponseEntity(oAuth2Service.oAuth2Redirect(registrationId), HttpStatus.MOVED_PERMANENTLY)
    }

    @Operation(summary = "OAuth2 로그인 후 Token 발급")
    @GetMapping("/code/{registrationId}")
    fun oAuth2Code(
        @RequestParam code: String,
        @PathVariable("registrationId") registrationId: String
    ): ResponseEntity<UserLoginResponseDto> {
        return ResponseEntity.ok().body(oAuth2Service.oAuth2Login(code, registrationId))
    }

}