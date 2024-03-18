package com.alpm.server.domain.user.entity

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.global.model.BaseTimeEntity
import jakarta.persistence.*

@Entity
class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val numOfTyping: Int,

    val numOfWord: Int,

    val numOfBlock: Int,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "my_algorithms",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "algorithm_id")]
    )
    val myAlgorithms: Set<Algorithm>,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "my_code_groups",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "code_group_id")]
    )
    val myCodeGroups: List<CodeGroup>

): BaseTimeEntity() {
}