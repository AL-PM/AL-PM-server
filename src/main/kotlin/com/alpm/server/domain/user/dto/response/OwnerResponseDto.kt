package com.alpm.server.domain.user.dto.response

import com.alpm.server.domain.user.entity.User

data class OwnerResponseDto (

    val id: Long,

    val name: String,

    val profile: String

) {

    constructor(owner: User): this (
        id = owner.id!!,
        name = owner.name,
        profile = owner.profile
    )

}