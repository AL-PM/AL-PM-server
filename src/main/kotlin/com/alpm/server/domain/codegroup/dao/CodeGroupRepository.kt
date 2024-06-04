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

    @Query("select cg " +
            "from CodeGroup cg " +
            "where (cg.visible = true or cg.owner = :user)")
    fun findCodeGroups(user: User, pageable: Pageable): Page<CodeGroup>

    @Query("select cg " +
            "from CodeGroup cg join UserCodeGroup ucg on ucg.codeGroup = cg " +
            "where ucg.user = :user and (cg.visible = true or cg.owner = :user)")
    fun findCodeGroupsByUser(user: User, pageable: Pageable) : Page<CodeGroup>

    @Query("select cg " +
            "from CodeGroup cg " +
            "where cg.owner = :owner and (cg.visible = true or cg.owner = :user)")
    fun findCodeGroupsByOwner(user: User, owner: User, pageable: Pageable): Page<CodeGroup>

    @Query("select cg " +
            "from CodeGroup cg " +
            "where (cg.visible = true or cg.owner = :user) " +
            "and (:language is null or cg.language = :language) " +
            "and (:verified is null or cg.verified = :verified) " +
            "and (:keyword is null " +
            "or cg.owner.name like concat('%', :keyword, '%') " +
            "or cg.name like concat('%', :keyword, '%'))")
    fun findCodeGroupsByLanguageAndVerifiedAndKeyword(
        language: Language?, verified: Boolean?, keyword: String?, user: User, pageable: Pageable
    ): Page<CodeGroup>


}