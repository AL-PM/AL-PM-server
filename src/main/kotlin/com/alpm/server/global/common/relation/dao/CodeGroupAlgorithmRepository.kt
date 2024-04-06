package com.alpm.server.global.common.relation.dao

import com.alpm.server.global.common.relation.entity.CodeGroupAlgorithm
import org.springframework.data.jpa.repository.JpaRepository

interface CodeGroupAlgorithmRepository: JpaRepository<CodeGroupAlgorithm, Long> {
}