package com.alpm.server.domain.algorithm.dto

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.Language
import java.time.LocalDateTime

data class AlgorithmCreateResponseDto (
            val id: Long,

            val owner : User {
                val id:Long,
                val name: String
            }

            val referencedCount: Int,

            val verified: Boolean, // Official-User 구분

            val language: Language, //(enum - C/C++, JAVA, Python),

            val name: String,

            val content: String,

            val description: String,

            val createdAt: LocalDateTime,

            val updatedAt: LocalDateTime
    ){
    constructor(algorithm: Algorithm): this(
            id = algorithm.id,
            referencedCount = algorithm.referencedCount,
            verified = algorithm.verified,
            language = algorithm.language,
            name = algorithm.name,
            content = algorithm.content,
            description = algorithm.description,
            createdAt = algorithm.createdAt,
            updatedAt = algorithm.updatedAt,
    )
}

/**
 * {
 * 	"id": Long,
 * 	"owner": {
 * 		"id": Long,
 * 		"name": String
 * 	},
 * 	"name": String,
 * 	"language": String(C, JAVA, Python),
 * 	"referencedCount": Int,
 * 	"verified": Boolean,
 * 	"content": String,
 * 	"description": String,
 * 	"createdAt": LocalDateTime,
 * 	"updatedAt": LocalDateTime
 * }
 */