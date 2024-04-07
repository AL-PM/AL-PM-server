package com.alpm.server.domain.algorithm.dto.request

import com.alpm.server.global.common.model.Language
import com.alpm.server.global.validation.Enum
import com.alpm.server.global.validation.ValidationGroup

data class AlgorithmSearchRequestDto (

    @field:Enum(
        message = "유효하지 않은 언어입니다",
        enumClass = Language::class,
        groups = [ValidationGroup.EnumGroup::class]
    )
    val language: String?,

    val verified: Boolean?,

    val keyword: String?

) {
}