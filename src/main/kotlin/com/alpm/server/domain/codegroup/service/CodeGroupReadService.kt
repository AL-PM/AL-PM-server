package com.alpm.server.domain.codegroup.service

import com.alpm.server.domain.algorithm.dto.response.AlgorithmDetailResponseDto
import org.springframework.stereotype.Service
import com.alpm.server.domain.codegroup.dao.CodeGroupRepository
import com.alpm.server.domain.codegroup.dto.request.CodeGroupSearchRequestDto
import com.alpm.server.domain.codegroup.dto.response.SimpleCodeGroupResponseDto
import com.alpm.server.domain.user.dao.UserRepository
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.Language
import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder

@Service
class CodeGroupReadService(

    private val codeGroupRepository: CodeGroupRepository,

    private val userRepository: UserRepository

) {

    fun readCodeGroupById(codeGroupId: Long): SimpleCodeGroupResponseDto {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        val codeGroup = codeGroupRepository.findByIdOrNull(codeGroupId)
            ?: throw CustomException(ErrorCode.CODE_GROUP_NOT_FOUND)

        if (!codeGroup.visible && codeGroup.owner.id != user.id) {
            throw CustomException(ErrorCode.CODE_GROUP_NOT_FOUND)
        }

        return SimpleCodeGroupResponseDto(codeGroup)
    }

    fun readCodeGroups(pageable: Pageable): Page<SimpleCodeGroupResponseDto> {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        val codeGroup = codeGroupRepository.findCodeGroups(user, pageable)

        return codeGroup.map { SimpleCodeGroupResponseDto(it) }
    }

    fun readCodeGroupsByUser(userId : Long, pageable: Pageable): Page<SimpleCodeGroupResponseDto> {
        val targetUser = userRepository.findByIdOrNull(userId) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val codeGroupsPage = codeGroupRepository.findCodeGroupsByUser(targetUser, pageable)

        return codeGroupsPage.map { SimpleCodeGroupResponseDto(it) }
    }

    fun readCodeGroupsByOwner(ownerId: Long, pageable: Pageable): Page<SimpleCodeGroupResponseDto> {
        val user = userRepository.findByIdOrNull(ownerId) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val codeGroup = codeGroupRepository.findCodeGroupsByOwner(user, pageable)

        return codeGroup.map { SimpleCodeGroupResponseDto(it) }
    }

    fun searchCodeGroups(request: CodeGroupSearchRequestDto, pageable: Pageable): Page<SimpleCodeGroupResponseDto> {
        val user = SecurityContextHolder.getContext().authentication.principal as User

        val codeGroup = codeGroupRepository.findCodeGroupsByLanguageAndVerifiedAndKeyword(
            language = if (request.language == null) {
                null
            } else {
                Language.valueOf(request.language)
            },
            verified = request.verified,
            keyword = request.keyword,
            user = user,
            pageable = pageable
        )

        return codeGroup.map { SimpleCodeGroupResponseDto(it) }
    }

    fun readRandomAlgorithmByCodeGroupId(codeGroupId: Long): AlgorithmDetailResponseDto {
        val codeGroup = codeGroupRepository.findByIdOrNull(codeGroupId)
            ?: throw CustomException(ErrorCode.CODE_GROUP_NOT_FOUND)

        val pick = codeGroup.algorithmList.random()

        return AlgorithmDetailResponseDto(pick.algorithm)
    }

}