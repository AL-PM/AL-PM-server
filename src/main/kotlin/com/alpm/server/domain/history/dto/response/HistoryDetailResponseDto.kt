package com.alpm.server.domain.history.dto.response

import com.alpm.server.domain.algorithm.dto.response.SimpleAlgorithmResponseDto
import com.alpm.server.domain.history.entity.History
import com.alpm.server.domain.history.entity.ProblemType
import java.time.LocalDateTime

data class HistoryDetailResponseDto (

    val id: Long,

    val problemType: ProblemType,

    val point: Int,

    val algorithm: SimpleAlgorithmResponseDto,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime

) {

    constructor(history: History): this(
        id = history.id,
        problemType = history.problemType,
        point = history.point,
        algorithm = SimpleAlgorithmResponseDto(history.algorithm),
        createdAt = history.createdAt,
        updatedAt = history.updatedAt
    )

}