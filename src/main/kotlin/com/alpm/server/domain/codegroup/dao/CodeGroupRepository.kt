package com.alpm.server.domain.codegroup.dao

import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.global.common.model.Language
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CodeGroupRepository: JpaRepository<CodeGroup, Long> {

    @Query("SELECT c FROM CodeGroup c " +
            "WHERE (:language is null or c.language = :language) " +
            "and (:verified is null or c.verified = :verified) " +
            "and (:keyword is null or c.owner.name LIKE CONCAT('%', :keyword, '%') or c.name LIKE CONCAT('%', :keyword, '%'))")
    fun findCodeGroupsByLanguageAndVerifiedAndKeyword(language: Language?, verified: Boolean?, keyword: String?): List<CodeGroup>

}