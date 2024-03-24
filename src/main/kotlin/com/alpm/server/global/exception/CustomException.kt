package com.alpm.server.global.exception

data class CustomException (

    val errorCode: ErrorCode

): Exception() {
}