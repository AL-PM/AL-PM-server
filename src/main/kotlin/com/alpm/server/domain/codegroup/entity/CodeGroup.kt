package com.alpm.server.domain.codegroup.entity

import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.BaseTimeEntity
import com.alpm.server.global.common.model.Language
import com.alpm.server.global.common.relation.entity.CodeGroupAlgorithm
import jakarta.persistence.*

@Entity
class CodeGroup (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    var referencedCount: Int = 0,

    val verified: Boolean = false,

    val visible: Boolean,

    val language: Language,

    // 해당 CodeGroup 제작자
    @ManyToOne(fetch = FetchType.LAZY)
    val owner: User,

    // 해당 CodeGroup에 속해 있는 Algorithm 리스트
    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "codeGroup"
    )
    val algorithmList: List<CodeGroupAlgorithm> = emptyList()

): BaseTimeEntity() {
}