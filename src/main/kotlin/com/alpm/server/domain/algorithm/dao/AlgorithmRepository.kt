package com.alpm.server.domain.algorithm.dao

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.global.common.model.Language
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AlgorithmRepository: JpaRepository<Algorithm, Long> {

    @Query("SELECT a FROM Algorithm a " +
            "WHERE (:language is null or a.language = :language) " +
            "and (:verified is null or a.verified = :verified) " +
            "and (:keyword is null or a.owner.name LIKE CONCAT('%', :keyword, '%') or a.name LIKE CONCAT('%', :keyword, '%'))")
    fun findAlgorithmsByLanguageAndVerifiedAndKeyword(language: Language?, verified: Boolean?, keyword: String?,pageable: Pageable): List<Algorithm>

}