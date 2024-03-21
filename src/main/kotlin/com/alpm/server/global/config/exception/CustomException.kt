package com.alpm.server.global.config.exception

data class CustomException (

    val errorCode: ErrorCode

): Exception() {
}