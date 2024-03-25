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

    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    val algorithm: Algorithm

    // todo: 문제 타입, 타수, 블록 수 등 정보 추가

): BaseTimeEntity() {
}