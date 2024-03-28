package com.alpm.server.domain.algorithm.dto

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.user.dto.OwnerDto
import com.alpm.server.global.common.model.Language
import java.time.LocalDateTime

data class AlgorithmDto (

    val id: Long,

    val name: String,

    val referencedCount: Int,

    val verified: Boolean, // Official-User 구분

    val language: Language, //(enum - C/C++, JAVA, Python),

    val content: String,

    val description: String,

    val owner: OwnerDto,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime

){

    constructor (algorithm: Algorithm): this(
        id = algorithm.id!!,
        referencedCount = algorithm.referencedCount,
        verified = algorithm.verified,
        language = algorithm.language,
        name = algorithm.name,
        content = algorithm.content,
        description = algorithm.description,
        owner = OwnerDto(algorithm.owner),
        createdAt = algorithm.createdAt,
        updatedAt = algorithm.updatedAt,
    )

}