package com.alpm.server.global.auth.jwt

import com.alpm.server.global.exception.ErrorCode
import com.alpm.server.global.exception.dto.ExceptionResponseDto
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationEntryPoint (

    private val objectMapper: ObjectMapper

): AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        val error = ErrorCode.INVALID_TOKEN
        response.status = error.status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "utf-8"
        response.writer.write(objectMapper.writeValueAsString(
            ExceptionResponseDto(
                status = error.status,
                requestUri = request.requestURI,
                data = error.message,
            )
        ))
    }

}