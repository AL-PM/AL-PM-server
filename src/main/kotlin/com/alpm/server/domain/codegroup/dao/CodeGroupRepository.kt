package com.alpm.server.domain.codegroup.dao

import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.Language
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CodeGroupRepository: JpaRepository<CodeGroup, Long> {

    @Query("SELECT cg FROM CodeGroup cg " +
            "JOIN UserCodeGroup ucg ON ucg.codeGroup = cg " +
            "WHERE ucg.user = :user " +
            "AND (cg.visible = true OR cg.owner = :user) " +
            "AND (:language is null OR cg.language = :language) " +
            "AND (:verified is null OR cg.verified = :verified) " +
            "AND (:keyword is null OR cg.owner.name LIKE CONCAT('%', :keyword, '%') OR cg.name LIKE CONCAT('%', :keyword, '%'))")
    fun findCodeGroupsByLanguageAndVerifiedAndKeyword(language: Language?, verified: Boolean?, keyword: String?,user: User,pageable: Pageable): Page<CodeGroup>

    fun findByOwner(owner: User, pageable: Pageable): Page<CodeGroup>

    @Query("SELECT cg FROM CodeGroup cg JOIN UserCodeGroup ucg ON ucg.codeGroup = cg WHERE ucg.user = :targetUser AND (cg.visible = true  OR  cg.owner = :targetUser)")
    fun findCodeGroupsByUserId(targetUser: User, pageable: Pageable) : Page<CodeGroup>


}