package com.alpm.server.domain.codegroup.dao

import com.alpm.server.domain.codegroup.entity.CodeGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CodeGroupRepository: JpaRepository<CodeGroup, Long> {
}