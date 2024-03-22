package com.alpm.server.global.config.exception

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handlerBaseException(
        e: CustomException,
        request: HttpServletRequest
    ): ResponseEntity<ExceptionResponseDto<String>> {
        return ResponseEntity.status(e.errorCode.status).body(
            ExceptionResponseDto(
                status = e.errorCode.status,
                requestUri = request.requestURI,
                data = e.errorCode.message
            )
        )
    }

}