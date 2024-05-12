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

    @Query("select distinct a " +
            "from Algorithm a " +
            "join CodeGroupAlgorithm cga on cga.algorithm = a " +
            "join cga.codeGroup cg " +
            "join UserCodeGroup ucg on ucg.codeGroup = cg " +
            "where ucg.user = :user and (cg.visible = true or cg.owner = :user)")
    fun findAlgorithmsByUser(user: User, pageable: Pageable): Page<Algorithm>

    fun findAlgorithmsByOwner(owner: User, pageable: Pageable): Page<Algorithm>

    @Query("select a " +
            "from Algorithm a join CodeGroupAlgorithm cga on cga.algorithm = a " +
            "where cga.codeGroup = :codeGroup")
    fun findAlgorithmsByCodeGroup(codeGroup: CodeGroup, pageable: Pageable): Page<Algorithm>

    @Query("select a from Algorithm a " +
            "where (:language is null or a.language = :language) " +
            "and (:verified is null or a.verified = :verified) " +
            "and (:keyword is null or a.owner.name like concat('%', :keyword, '%') " +
            "or a.name like concat('%', :keyword, '%'))")
    fun findAlgorithmsByLanguageAndVerifiedAndKeyword(
        language: Language?, verified: Boolean?, keyword: String?, pageable: Pageable
    ): Page<Algorithm>

}