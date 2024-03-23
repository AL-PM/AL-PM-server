package com.alpm.server.domain.user.controller

import com.alpm.server.domain.user.dto.UserDto
import com.alpm.server.global.auth.oauth2.OAuth2Service
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/oauth2")
class UserOAuth2Controller (

    private val oAuth2Service: OAuth2Service

) {

    @GetMapping("/authorization/{registrationId}")
    fun oAuth2Redirect(
        @PathVariable("registrationId") registrationId: String
    ): ResponseEntity<HttpHeaders> {
        return ResponseEntity(oAuth2Service.oAuth2Redirect(registrationId), HttpStatus.MOVED_PERMANENTLY)
    }

    @GetMapping("/code/{registrationId}")
    fun oAuth2Code(
        @RequestParam code: String,
        @PathVariable("registrationId") registrationId: String
    ): ResponseEntity<UserDto> {
        return ResponseEntity.ok().body(oAuth2Service.oAuth2Login(code, registrationId))
    }

}