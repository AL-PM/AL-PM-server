package com.alpm.server.domain.codegroup.entity

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.model.BaseTimeEntity
import com.alpm.server.global.model.Language
import jakarta.persistence.*

@Entity
class CodeGroup (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val visible: Boolean,

    val language: Language,

    @ManyToOne(fetch = FetchType.LAZY)
    val owner: User,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "groups_algorithms",
        joinColumns = [JoinColumn(name = "code_group_id")],
        inverseJoinColumns = [JoinColumn(name = "algorithm_id")]
    )
    val groupsAlgorithms: List<Algorithm>,

    // 사용하지 않는 attribute
    @ManyToMany(
        fetch = FetchType.LAZY,
        mappedBy = "myCodeGroups"
    )
    val myCodeGroups: List<User>

): BaseTimeEntity() {
}