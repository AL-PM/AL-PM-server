package com.alpm.server.domain.user.dto.response

data class UserLoginResponseDto (

    val user: SimpleUserResponseDto,

    val accessToken: String,

    val refreshToken: String

) {
}
