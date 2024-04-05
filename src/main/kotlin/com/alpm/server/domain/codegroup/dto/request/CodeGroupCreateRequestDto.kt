package com.alpm.server.domain.codegroup.dto.request

import com.alpm.server.global.common.model.Language
import com.alpm.server.global.validation.Enum
import com.alpm.server.global.validation.ValidationGroup.*
import jakarta.validation.constraints.NotBlank

class CodeGroupCreateRequestDto(
    @field:NotBlank(
        message = "코드그룹 제목은 필수 값 입니다",
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
        message = "그룹 공개 여부는 필수 값 입니다",
        groups = [NotNullGroup::class]
    )
    val visible: Boolean?
) {
}