package com.alpm.server.domain.user.dto

data class LoginResponseDto (

    val user: UserDto,

    val accessToken: String,

    val refreshToken: String

) {
}
