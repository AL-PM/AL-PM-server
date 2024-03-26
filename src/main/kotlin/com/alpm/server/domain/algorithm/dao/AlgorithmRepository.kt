package com.alpm.server.domain.algorithm.dao

import com.alpm.server.domain.algorithm.entity.Algorithm
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AlgorithmRepository: JpaRepository<Algorithm, Long> {
}