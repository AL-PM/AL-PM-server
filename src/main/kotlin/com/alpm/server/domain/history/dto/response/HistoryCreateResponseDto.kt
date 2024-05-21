package com.alpm.server.domain.history.dto.response

import com.alpm.server.domain.history.entity.History
import com.alpm.server.global.common.model.ProblemType
import java.time.LocalDateTime

data class HistoryCreateResponseDto(

    val id:Long,

    val problemType: ProblemType,

    val point: Int,

    val userId: Long,

    val algorithmId: Long,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime

){
    constructor(history: History): this(
        id = history.id!!,
        problemType = history.problemType,
        point = history.point,
        userId = history.user.id!!,
        algorithmId = history.algorithm.id!!,
        createdAt = history.createdAt,
        updatedAt = history.updatedAt
    )
}