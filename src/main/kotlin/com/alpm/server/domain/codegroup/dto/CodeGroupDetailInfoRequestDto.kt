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

    val algorithms:List<SimpleAlgorithmDto>,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime
) {
    constructor(codegroup : CodeGroup): this(
        id = codegroup.id!!,
        visible = codegroup.visible,
        language = codegroup.language,
        owner = OwnerDto(codegroup.owner),
        algorithms = codegroup.groupsAlgorithms.map { SimpleAlgorithmDto(it) },
        createdAt = codegroup.createdAt,
        updatedAt = codegroup.updatedAt,
    )
}