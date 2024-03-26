package com.alpm.server.global.exception.dto

data class ValidationErrorFieldDto (

    val field: String,

    val message: String

) {
}