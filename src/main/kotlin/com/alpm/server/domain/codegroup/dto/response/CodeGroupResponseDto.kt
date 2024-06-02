package com.alpm.server.domain.codegroup.dto.response

import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.domain.user.dto.response.OwnerResponseDto
import com.alpm.server.global.common.model.Language
import java.time.LocalDateTime

data class CodeGroupResponseDto (
    val id: Long,

    val name: String,

    val referencedCount: Int,

    val verified: Boolean,

    val visible: Boolean,

    val language: Language, //(enum - C/C++, JAVA, Python),

    val owner: OwnerResponseDto,

    val algorithmCount: Int,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime
){
    constructor (codeGroup: CodeGroup): this(
        id = codeGroup.id!!,
        name = codeGroup.name,
        referencedCount = codeGroup.referencedCount,
        verified = codeGroup.verified,
        visible = codeGroup.visible,
        language = codeGroup.language,
        owner = OwnerResponseDto(codeGroup.owner),
        algorithmCount = codeGroup.algorithmList.size,
        createdAt = codeGroup.createdAt,
        updatedAt = codeGroup.updatedAt
    )
}