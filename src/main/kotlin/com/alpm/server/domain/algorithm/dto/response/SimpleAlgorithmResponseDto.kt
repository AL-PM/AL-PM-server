package com.alpm.server.domain.algorithm.dto.response

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.user.dto.response.OwnerResponseDto
import com.alpm.server.global.common.model.Language
import java.time.LocalDateTime

data class SimpleAlgorithmResponseDto (

    val id: Long,

    val name: String,

    val referencedCount: Int,

    val verified: Boolean, // Official-User 구분

    val language: Language, //(enum - C/C++, JAVA, Python),

    var deleted: Boolean,

    val owner: OwnerResponseDto,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime

) {

    constructor (algorithm: Algorithm): this(
        id = algorithm.id!!,
        name = algorithm.name,
        referencedCount = algorithm.referencedCount,
        verified = algorithm.verified,
        language = algorithm.language,
        deleted = algorithm.deleted,
        owner = OwnerResponseDto(algorithm.owner),
        createdAt = algorithm.createdAt,
        updatedAt = algorithm.updatedAt,
    )

}