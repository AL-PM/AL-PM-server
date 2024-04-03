package com.alpm.server.domain.codegroup.dto

import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.domain.user.dto.OwnerDto
import com.alpm.server.global.common.model.Language
import java.time.LocalDateTime

data class CodeGroupDto(

    val id: Long,

    val name: String,

    val language: Language, //(enum - C/C++, JAVA, Python),

    val visible: Boolean,

    val owner: OwnerDto,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime

) {

    constructor (codegroup: CodeGroup): this(
        id = codegroup.id!!,
        name = codegroup.name,
        language = codegroup.language,
        visible = codegroup.visible,
        owner = OwnerDto(codegroup.owner),
        createdAt = codegroup.createdAt,
        updatedAt = codegroup.updatedAt,
    )
}