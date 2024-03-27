package com.alpm.server.domain.user.entity

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.domain.history.entity.History
import com.alpm.server.global.common.model.BaseTimeEntity
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

    val tracePoint: Int = 0,

    val fillPoint: Int = 0,

    val blockPoint: Int = 0,

    val sequencePoint: Int = 0,

    // 해당 User의 CodeGroup들 중 하나에라도 속해 있는 Algorithm 리스트
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "my_algorithms",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "algorithm_id")]
    )
    val myAlgorithms: Set<Algorithm> = emptySet(),

    // 해당 User의 CodeGroup 리스트
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "my_code_groups",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "code_group_id")]
    )
    val myCodeGroups: List<CodeGroup> = emptyList(),

    // 해당 User가 제작한 Algorithm 리스트
    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "owner"
    )
    val ownedAlgorithmList: List<Algorithm> = emptyList(),

    // 해당 User의 Algorithm 문제 해결 기록
    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "user"
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