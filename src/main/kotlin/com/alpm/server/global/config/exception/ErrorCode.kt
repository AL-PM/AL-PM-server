package com.alpm.server.global.config.exception

import org.springframework.http.HttpStatus

enum class ErrorCode (

    val status: HttpStatus,

    val message: String

) {

    ;

}