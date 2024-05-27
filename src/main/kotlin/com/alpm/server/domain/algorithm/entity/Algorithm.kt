package com.alpm.server.domain.algorithm.entity

import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.BaseTimeEntity
import com.alpm.server.global.common.model.Language
import jakarta.persistence.*

@Entity
class Algorithm(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    var referencedCount: Int = 0,

    val verified: Boolean = false,

    val language: Language,

    @Column(columnDefinition = "LONGTEXT")
    val original: String,

    @Column(columnDefinition = "LONGTEXT")
    val content: String,

    @Column(columnDefinition = "LONGTEXT")
    val description: String,

    // 해당 Algorithm 제작자
    @ManyToOne(fetch = FetchType.LAZY)
    val owner: User,

): BaseTimeEntity() {
}