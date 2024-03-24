package com.alpm.server.domain.user.dto

import com.alpm.server.domain.user.entity.User
import java.time.LocalDateTime

data class UserDto (

    val id: Long,

    val name: String,

    val provider: String,

    val uid: String,

    val numOfTyping: Int,

    val numOfWord: Int,

    val numOfBlock: Int,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime

) {

    constructor(user: User): this(
        id = user.id!!,
        name = user.name,
        provider = user.provider,
        uid = user.uid,
        numOfTyping = user.numOfTyping,
        numOfWord = user.numOfWord,
        numOfBlock = user.numOfBlock,
        createdAt = user.createdAt,
        updatedAt = user.updatedAt
    )

}