package com.alpm.server.domain.codegroup.service

import org.springframework.stereotype.Service
import com.alpm.server.domain.codegroup.dao.CodeGroupRepository
import com.alpm.server.domain.codegroup.dto.*
import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.domain.user.dao.UserRepository
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.Language
import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder

@Service
class CodeGroupService(

    private val codeGroupRepository: CodeGroupRepository,

    private val userRepository: UserRepository

) {
    fun saveCodeGroup(request: CodeGroupCreateRequestDto): CodeGroupDto {
        val user = SecurityContextHolder.getContext().authentication.principal as User

        val codeGroup =codeGroupRepository.save(
            CodeGroup(
                name = request.name!!,
                visible = request.visible!!,
                language = Language.valueOf(request.language!!),
                owner = user,
            )
        )
        return CodeGroupDto(codeGroup)
    }

    fun readAllCodeGroups(): List<CodeGroupListResponseDto> {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        return codeGroupRepository.findAll().filter { it.visible || (!it.visible && it.owner==user) }.map { CodeGroupListResponseDto(it) }
    }

    fun readAllCodeGroupsByUserID(id : Long): List<CodeGroupListResponseDto> {
        val currentUser = SecurityContextHolder.getContext().authentication.principal as User
        val targetUser = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        return targetUser.myCodeGroups.filter { it.visible || (!it.visible && it.owner==currentUser) }.map { CodeGroupListResponseDto(it) }
    }

    fun readCodeGroupById(id: Long): CodeGroupDto {
        val codeGroup = codeGroupRepository.findByIdOrNull(id)?:throw Exception()
        return CodeGroupDto(codeGroup)
    }
}