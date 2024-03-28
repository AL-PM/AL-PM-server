package com.alpm.server.domain.user.dto.response

import com.alpm.server.domain.user.dto.UserDto

data class UserLoginResponseDto (

    val user: UserDto,

    val accessToken: String,

    val refreshToken: String

) {
}
