package com.alpm.server.domain.user.entity

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.domain.history.entity.History
import com.alpm.server.global.common.model.BaseTimeEntity
import com.alpm.server.global.common.relation.entity.UserCodeGroup
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var name: String,

    val provider: String,

    val uid: String,

    var profile: String,

    var tracePoint: Int = 0,

    var fillPoint: Int = 0,

    var blockPoint: Int = 0,

    var sequencePoint: Int = 0,

    // 해당 User의 CodeGroup 리스트
    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "user"
    )
    val codeGroupList: List<UserCodeGroup> = emptyList(),

    // 해당 User가 제작한 Algorithm 리스트
    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "owner"
    )
    val ownedAlgorithmList: List<Algorithm> = emptyList(),

    // 해당 User가 제작한 CodeGroup 리스트
    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "owner"
    )
    val ownedCodeGroupList: List<CodeGroup> = emptyList(),

    // 해당 User의 Algorithm 문제 해결 기록
    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "user",
    )
    val historyList: List<History> = emptyList()

): BaseTimeEntity(), UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String {
        return id.toString()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}