package com.alpm.server.domain.algorithm.dao

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.Language
import org.springframework.data.domain.Page
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
    fun findAlgorithmsByLanguageAndVerifiedAndKeyword(language: Language?, verified: Boolean?, keyword: String?,pageable: Pageable): Page<Algorithm>

    fun findAlgorithmByOwner(owner: User, pageable: Pageable): Page<Algorithm>

    @Query("SELECT DISTINCT a FROM Algorithm a JOIN CodeGroupAlgorithm cga ON cga.algorithm = a WHERE cga.codeGroup = :codeGroup")
    fun findAlgorithmByGroupId(codeGroup: CodeGroup, pageable: Pageable): Page<Algorithm>


}