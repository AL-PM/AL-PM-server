package com.alpm.server.global.common.relation.dao

import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.relation.entity.UserCodeGroup
import org.springframework.data.jpa.repository.JpaRepository

interface UserCodeGroupRepository: JpaRepository<UserCodeGroup, Long> {

    fun existsByUserAndCodeGroup(user: User, codeGroup: CodeGroup): Boolean

}