package com.alpm.server.global.common.relation.entity

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.codegroup.entity.CodeGroup
import jakarta.persistence.*

@Entity
class CodeGroupAlgorithm (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val codeGroup: CodeGroup,

    @ManyToOne(fetch = FetchType.LAZY)
    val algorithm: Algorithm

) {
}