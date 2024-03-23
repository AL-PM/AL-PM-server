package com.alpm.server.domain.algorithm.entity

import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.BaseTimeEntity
import com.alpm.server.global.common.model.Language
import jakarta.persistence.*

@Entity
class Algorithm (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val referencedCount: Int,

    val verified: Boolean,

    val language: Language,

    @Column(columnDefinition = "LONGTEXT")
    val content: String,

    @Column(columnDefinition = "LONGTEXT")
    val description: String,

    // 사용하지 않는 attribute
    @ManyToMany(
        fetch = FetchType.LAZY,
        mappedBy = "myAlgorithms"
    )
    val myAlgorithms: List<User>,

    // 사용하지 않는 attribute
    @ManyToMany(
        fetch = FetchType.LAZY,
        mappedBy = "groupsAlgorithms"
    )
    val groupsAlgorithms: List<CodeGroup>

): BaseTimeEntity() {
}