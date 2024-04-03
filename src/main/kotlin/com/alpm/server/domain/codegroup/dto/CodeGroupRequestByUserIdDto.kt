package com.alpm.server.domain.codegroup.dto

import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.global.common.model.Language
import java.time.LocalDateTime

class CodeGroupRequestByUserIdDto(
    val id: Long?,

    val name: String,

    val visible: Boolean,

    val language: Language, //(enum - C/C++, JAVA, Python),

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime
) {
    constructor(codegroup: CodeGroup): this(
        id = codegroup.id,
        name = codegroup.name,
        visible = codegroup.visible,
        language = codegroup.language,
        createdAt = codegroup.createdAt,
        updatedAt = codegroup.updatedAt
    )
}