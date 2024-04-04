package com.alpm.server.domain.codegroup.dto

import com.alpm.server.domain.algorithm.dto.SimpleAlgorithmDto
import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.domain.user.dto.OwnerDto
import com.alpm.server.global.common.model.Language
import java.time.LocalDateTime

class CodeGroupDetailInfoRequestDto(
    val id: Long,

    val visible: Boolean,

    val language: Language, //(enum - C/C++, JAVA, Python),

    val owner: OwnerDto,

    val groupsAlgorithms:List<SimpleAlgorithmDto>,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime
) {
    constructor(codeGroup : CodeGroup): this(
        id = codeGroup.id!!,
        visible = codeGroup.visible,
        language = codeGroup.language,
        owner = OwnerDto(codeGroup.owner),
        groupsAlgorithms = codeGroup.groupsAlgorithms.map { SimpleAlgorithmDto(it) },
        createdAt = codeGroup.createdAt,
        updatedAt = codeGroup.updatedAt,
    )
}