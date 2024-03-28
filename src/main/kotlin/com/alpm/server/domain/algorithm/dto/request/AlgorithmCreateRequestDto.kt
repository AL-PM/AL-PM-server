package com.alpm.server.domain.algorithm.dto.request

import com.alpm.server.global.common.model.Language
import com.alpm.server.global.validation.Enum
import com.alpm.server.global.validation.ValidationGroup.*
import jakarta.validation.constraints.NotBlank

data class AlgorithmCreateRequestDto(

    @field:NotBlank(
        message = "알고리즘 제목은 필수 값 입니다",
        groups = [NotNullGroup::class]
    )
    val name: String?,

    @field:Enum(
        message = "유효하지 않은 언어입니다",
        enumClass = Language::class,
        groups = [EnumGroup::class]
    )
    val language: String?, 

    @field:NotBlank(
        message = "알고리즘 내용은 필수 값 입니다",
        groups = [NotNullGroup::class]
    )
    val content: String?,

    @field:NotBlank(
        message = "알고리즘 설명은 필수 값 입니다",
        groups = [NotNullGroup::class]
    )
    val description: String?,

) {
}