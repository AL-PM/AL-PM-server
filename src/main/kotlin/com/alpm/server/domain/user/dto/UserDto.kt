package com.alpm.server.domain.user.dto

import com.alpm.server.domain.user.entity.User
import java.time.LocalDateTime

data class UserDto (

    val id: Long,

    val name: String,

    val provider: String,

    val uid: String,

    val profile: String,

    val tracePoint: Int,

    val fillPoint: Int,

    val blockPoint: Int,

    val sequencePoint: Int,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime

) {

    constructor(user: User): this(
        id = user.id!!,
        name = user.name,
        provider = user.provider,
        uid = user.uid,
        profile = user.profile,
        tracePoint = user.tracePoint,
        fillPoint = user.fillPoint,
        blockPoint = user.blockPoint,
        sequencePoint = user.sequencePoint,
        createdAt = user.createdAt,
        updatedAt = user.updatedAt
    )

}