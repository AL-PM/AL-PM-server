package com.alpm.server.domain.codegroup.dto

import com.alpm.server.global.common.model.Language
import java.time.LocalDateTime

class CodegroupListRequestDto (
    val id: Long,

    val name: String,

    val language: Language, //(enum - C/C++, JAVA, Python),

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime
){
}