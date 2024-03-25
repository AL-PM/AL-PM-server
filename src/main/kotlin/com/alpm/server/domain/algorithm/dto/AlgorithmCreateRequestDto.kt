package com.alpm.server.domain.algorithm.dto

import com.alpm.server.global.common.model.Language

data class AlgorithmCreateRequestDto(

    val name: String,

    val language: Language, //(enum - C/C++, JAVA, Python),

    val content: String,

    val description: String,

) {
}