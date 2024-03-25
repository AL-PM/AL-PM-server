package com.alpm.server.domain.algorithm.dto

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.global.common.model.Language
import java.time.LocalDateTime

data class AlgorithmDto (

        val id: Long,

        val referencedCount: Int,

        val verified: Boolean, // Official-User 구분

        val language: Language, //(enum - C/C++, JAVA, Python),

        val name: String,

        val content: String,

        val description: String,

        val createdAt: LocalDateTime,

        val updatedAt: LocalDateTime

) {

    constructor(Algorithm: Algorithm): this(
            id = Algorithm.id,
            referencedCount = Algorithm.referencedCount,
            verified = Algorithm.verified,
            language = Algorithm.language,
            name = Algorithm.name,
            content = Algorithm.content,
            description = Algorithm.description,
            createdAt = Algorithm.createdAt,
            updatedAt = Algorithm.updatedAt,
    )
}