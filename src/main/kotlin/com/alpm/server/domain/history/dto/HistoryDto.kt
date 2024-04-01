package com.alpm.server.domain.history.dto

import com.alpm.server.domain.algorithm.dto.SimpleAlgorithmDto
import com.alpm.server.domain.history.entity.History
import com.alpm.server.domain.history.entity.ProblemType
import java.time.LocalDateTime

data class HistoryDto (

    val id: Long,

    val problemType: ProblemType,

    val point: Int,

    val algorithm: SimpleAlgorithmDto,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime

) {

    constructor(history: History): this(
        id = history.id,
        problemType = history.problemType,
        point = history.point,
        algorithm = SimpleAlgorithmDto(history.algorithm),
        createdAt = history.createdAt,
        updatedAt = history.updatedAt
    )

}