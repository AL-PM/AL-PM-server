package com.alpm.server.domain.algorithm.dao

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.global.common.model.Language
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AlgorithmRepository: JpaRepository<Algorithm, Long> {
}