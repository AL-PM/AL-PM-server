package com.alpm.server.global.exception

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

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

    @ExceptionHandler(NoResourceFoundException::class)
    fun noResourceFoundExceptionHandler(
        e: NoResourceFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ExceptionResponseDto<String>> {
        val errorCode = ErrorCode.WRONG_URL
        return ResponseEntity.status(errorCode.status).body(
            ExceptionResponseDto(
                status = errorCode.status,
                requestUri = request.requestURI,
                data = errorCode.message
            )
        )
    }

}