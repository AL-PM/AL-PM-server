package com.alpm.server.domain.history.dto.request

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.history.entity.ProblemType
import com.alpm.server.domain.user.entity.User

data class HistoryCreateRequestDto (

    val problemType: ProblemType,

    val point: Int,

    val user: User,

    val algorithm:Algorithm

){
}