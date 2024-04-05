package com.alpm.server.domain.codegroup.dto.response

import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.global.common.model.Language
import java.time.LocalDateTime

class CodeGroupListResponseDto(
    val id: Long,

    val name: String,

    val visible: Boolean,

    val language: Language, //(enum - C/C++, JAVA, Python),

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime
){
    constructor (codeGroup: CodeGroup): this(
        id = codeGroup.id!!,
        name = codeGroup.name,
        visible = codeGroup.visible,
        language = codeGroup.language,
        createdAt = codeGroup.createdAt,
        updatedAt = codeGroup.updatedAt
    )
}