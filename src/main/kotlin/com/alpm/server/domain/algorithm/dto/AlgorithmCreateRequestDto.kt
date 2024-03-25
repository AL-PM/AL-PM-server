package com.alpm.server.domain.algorithm.dto

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.global.common.model.Language
import java.time.LocalDateTime

data class AlgorithmCreateRequestDto(

        val name: String,
        val language: Language, //(enum - C/C++, JAVA, Python),
        val content: String,
        val description: String,
) {
    constructor(algorithm: AlgorithmCreateRequestDto): this(
            name = algorithm.name,
            language = algorithm.language,
            content = algorithm.content,
            description = algorithm.description
    )
}