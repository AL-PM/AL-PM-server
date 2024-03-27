package com.alpm.server.domain.history.entity

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.BaseTimeEntity
import jakarta.persistence.*

@Entity
class History (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val problemType: ProblemType,

    val point: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    val algorithm: Algorithm

): BaseTimeEntity() {
}