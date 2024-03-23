package com.alpm.server.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode (

    val status: HttpStatus,

    val message: String

) {

    OAUTH2_PROVIDER_ERROR(HttpStatus.BAD_REQUEST, "잘못된 OAuth 제공자입니다"),
    OAUTH2_FAILURE(HttpStatus.BAD_REQUEST, "OAuth 인증에 실패하였습니다"),

    WRONG_URL(HttpStatus.NOT_FOUND, "잘못된 url 입니다"),
    USER_NOT_FOUNT(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다"),

    INVALID_TOKEN(HttpStatus.FORBIDDEN, "잘못된 토큰입니다")
    ;

}