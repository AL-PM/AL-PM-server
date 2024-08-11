package com.alpm.server.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode (

    val status: HttpStatus,

    val message: String

) {

    OAUTH2_PROVIDER_ERROR(HttpStatus.BAD_REQUEST, "잘못된 OAuth 제공자입니다"),
    OAUTH2_FAILURE(HttpStatus.BAD_REQUEST, "OAuth 인증에 실패하였습니다"),
    CODE_GROUP_EXIST(HttpStatus.BAD_REQUEST, "이미 추가된 코드그룹입니다"),
    ALGORITHM_EXIST(HttpStatus.BAD_REQUEST, "이미 추가된 알고리즘입니다"),
    NOT_YOUR_CODE_GROUP(HttpStatus.BAD_REQUEST, "권한이 없는 코드그룹입니다"),
    LANGUAGE_MISMATCH(HttpStatus.BAD_REQUEST, "언어가 일치하지 않습니다"),

    USER_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 삭제된 유저입니다"),
    NO_GRANT(HttpStatus.BAD_REQUEST, "권한이 없습니다"),

    WRONG_URL(HttpStatus.NOT_FOUND, "잘못된 url 입니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다"),
    ALGORITHM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 알고리즘을 찾을 수 없습니다"),
    CODE_GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 코드그룹을 찾을 수 없습니다"),

    INVALID_TOKEN(HttpStatus.FORBIDDEN, "잘못된 토큰입니다")
    ;

}