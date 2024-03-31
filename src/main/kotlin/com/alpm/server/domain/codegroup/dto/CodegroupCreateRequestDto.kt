package com.alpm.server.domain.codegroup.dto

import com.alpm.server.global.common.model.Language
import com.alpm.server.global.validation.Enum
import com.alpm.server.global.validation.ValidationGroup
import jakarta.validation.constraints.NotBlank

class CodegroupCreateRequestDto(
    val name: String?,

    val language: String?,

    val visible: Boolean
) {
}