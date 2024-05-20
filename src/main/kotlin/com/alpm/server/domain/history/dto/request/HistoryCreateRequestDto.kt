package com.alpm.server.domain.history.dto.request

import com.alpm.server.global.common.model.ProblemType
import com.alpm.server.global.validation.Enum
import com.alpm.server.global.validation.ValidationGroup.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class HistoryCreateRequestDto (

    @field:Enum(
        message = "유효하지 않은 문제 형식입니다.",
        enumClass = ProblemType::class,
        groups = [EnumGroup::class]
    )
    val problemType: ProblemType,

    @field:NotBlank(
        message = "학습 점수는 필수값 입니다.",
        groups = [NotNullGroup::class]
    )
    @field:Min(
        message = "학습 점수는 자연수만 입력 가능합니다",
        value = 1,
        groups = [MinMaxGroup::class]
    )
    val point: Int,

    @field:NotBlank(
        message = "알고리즘 ID는 필수값 입니다.",
        groups = [NotNullGroup::class]
    )
    val algorithmId:Long

){
}