package com.alpm.server.domain.codegroup.service

import com.alpm.server.domain.codegroup.dao.CodeGroupRepository
import com.alpm.server.domain.codegroup.dto.request.CodeGroupCreateRequestDto
import com.alpm.server.domain.codegroup.dto.response.CodeGroupResponseDto
import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.Language
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class CodeGroupCreateService (

    private val codeGroupRepository: CodeGroupRepository

) {

    fun createCodeGroup(request: CodeGroupCreateRequestDto): CodeGroupResponseDto {
        val user = SecurityContextHolder.getContext().authentication.principal as User

        val codeGroup = codeGroupRepository.save(
            CodeGroup(
                name = request.name!!,
                visible = request.visible!!,
                language = Language.valueOf(request.language!!),
                owner = user,
            )
        )
        return CodeGroupResponseDto(codeGroup)
    }

}