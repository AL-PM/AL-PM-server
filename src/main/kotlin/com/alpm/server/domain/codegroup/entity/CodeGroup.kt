package com.alpm.server.domain.codegroup.entity

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.BaseTimeEntity
import com.alpm.server.global.common.model.Language
import jakarta.persistence.*

@Entity
class CodeGroup (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    val referencedCount: Int = 0,

    val verified: Boolean = false,

    val visible: Boolean,

    val language: Language,

    // 해당 CodeGroup 제작자
    @ManyToOne(fetch = FetchType.LAZY)
    val owner: User,

    // 해당 CodeGroup에 속해 있는 Algorithm 리스트
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "groups_algorithms",
        joinColumns = [JoinColumn(name = "code_group_id")],
        inverseJoinColumns = [JoinColumn(name = "algorithm_id")]
    )
    val groupsAlgorithms: List<Algorithm> = emptyList(),

    // 사용하지 않는 attribute
    @ManyToMany(
        fetch = FetchType.LAZY,
        mappedBy = "myCodeGroups"
    )
    val myCodeGroups: List<User> = emptyList()

): BaseTimeEntity() {
}