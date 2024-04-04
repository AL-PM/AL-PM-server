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

    constructor (codeGroup: CodeGroup): this(
        id = codeGroup.id!!,
        name = codeGroup.name,
        language = codeGroup.language,
        visible = codeGroup.visible,
        owner = OwnerDto(codeGroup.owner),
        createdAt = codeGroup.createdAt,
        updatedAt = codeGroup.updatedAt,
    )
}