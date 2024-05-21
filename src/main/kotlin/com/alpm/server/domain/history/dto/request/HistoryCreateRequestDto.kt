package com.alpm.server.domain.history.dto.request

import com.alpm.server.global.common.model.ProblemType
import com.alpm.server.global.validation.Enum
import com.alpm.server.global.validation.ValidationGroup.*
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Range

data class HistoryCreateRequestDto (

    @field:Enum(
        message = "유효하지 않은 문제 형식입니다.",
        enumClass = ProblemType::class,
        groups = [EnumGroup::class]
    )
    val problemType: String?,

    @field:NotNull(
        message = "획득 점수는 필수 값 입니다.",
        groups = [NotNullGroup::class]
    )
    @field: Range(
        message = "획득 점수는 1에서 10000 사이의 자연수여야 합니다.",
        min = 1,
        max =  10000,
        groups = [NotNullGroup::class]
    )
    val point: Int?,

    @field:NotNull(
        message = "알고리즘 ID는 필수값 입니다.",
        groups = [NotNullGroup::class]
    )
    val algorithmId:Long?

){
}