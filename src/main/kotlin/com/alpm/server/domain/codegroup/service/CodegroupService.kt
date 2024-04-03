package com.alpm.server.domain.codegroup.service

import org.springframework.stereotype.Service
import com.alpm.server.domain.codegroup.dao.CodeGroupRepository
import com.alpm.server.domain.codegroup.dto.CodegroupCreateRequestDto
import com.alpm.server.domain.codegroup.dto.CodegroupDto
import com.alpm.server.domain.codegroup.dto.CodegroupListRequestDto
import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.Language
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder


@Service
class CodegroupService(
    private val codegroupRepository: CodeGroupRepository
) {
    fun saveCodegroup(request: CodegroupCreateRequestDto): CodegroupDto {
        val user = SecurityContextHolder.getContext().authentication.principal as User

        val codegroup =codegroupRepository.save(
            CodeGroup(
                name = request.name!!,
                visible = request.visible,
                language = Language.valueOf(request.language!!),
                owner = user,
            )
        )
        return CodegroupDto(codegroup)
    }

    fun readAllCodegroups(): List<CodegroupListRequestDto> {
        return codegroupRepository.findAll().map { CodegroupListRequestDto(it) }
    }

}