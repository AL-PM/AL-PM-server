package com.alpm.server.domain.codegroup.service

import com.alpm.server.domain.algorithm.dao.AlgorithmRepository
import org.springframework.stereotype.Service
import com.alpm.server.domain.codegroup.dao.CodeGroupRepository
import com.alpm.server.domain.codegroup.dto.request.CodeGroupCreateRequestDto
import com.alpm.server.domain.codegroup.dto.request.CodeGroupSearchRequestDto
import com.alpm.server.domain.codegroup.dto.response.CodeGroupDetailResponseDto
import com.alpm.server.domain.codegroup.dto.response.SimpleCodeGroupResponseDto
import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.domain.user.dao.UserRepository
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.Language
import com.alpm.server.global.common.relation.dao.CodeGroupAlgorithmRepository
import com.alpm.server.global.common.relation.entity.UserCodeGroup
import com.alpm.server.global.common.relation.dao.UserCodeGroupRepository
import com.alpm.server.global.common.relation.entity.CodeGroupAlgorithm
import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder

@Service
class CodeGroupService(

    private val codeGroupRepository: CodeGroupRepository,

    private val userRepository: UserRepository,

    private val algorithmRepository: AlgorithmRepository,

    private val userCodeGroupRepository: UserCodeGroupRepository,

    private val codeGroupAlgorithmRepository: CodeGroupAlgorithmRepository

) {
    fun saveCodeGroup(request: CodeGroupCreateRequestDto): CodeGroupDetailResponseDto {
        val user = SecurityContextHolder.getContext().authentication.principal as User

        val codeGroup =codeGroupRepository.save(
            CodeGroup(
                name = request.name!!,
                visible = request.visible!!,
                language = Language.valueOf(request.language!!),
                owner = user,
            )
        )
        return CodeGroupDetailResponseDto(codeGroup)
    }

    fun importCodeGroupById(id: Long) {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        var codeGroup = codeGroupRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.CODE_GROUP_NOT_FOUND)

        if (userCodeGroupRepository.existsByUserAndCodeGroup(user, codeGroup)) {
            throw CustomException(ErrorCode.CODE_GROUP_EXIST)
        }

        codeGroup.referencedCount++
        codeGroup = codeGroupRepository.save(codeGroup)

        userCodeGroupRepository.save(
            UserCodeGroup(
                user = user,
                codeGroup = codeGroup
            )
        )
    }

    fun readAllCodeGroups(pageable: Pageable): Page<SimpleCodeGroupResponseDto> {
        val user = SecurityContextHolder.getContext().authentication.principal as User

        val codeGroup = codeGroupRepository.findAll(pageable)
            .filter {
                it.visible || it.owner.id == user.id
            }
            .map {
                SimpleCodeGroupResponseDto(it)
            }
            .toList()

        return PageImpl(codeGroup, pageable, codeGroup.size.toLong())
    }

    fun readCodeGroupsByUserId(id : Long, pageable: Pageable): Page<SimpleCodeGroupResponseDto> {
        // todo: Pageable 수정
        val currentUser = SecurityContextHolder.getContext().authentication.principal as User
        val targetUser = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val codeGroup = targetUser.codeGroupList
            .map {
                it.codeGroup
            }
            .filter {
                it.visible || it.owner.id!! == currentUser.id!!
            }
            .map {
                SimpleCodeGroupResponseDto(it)
            }

        val start = pageable.offset.toInt()
        val end = (start + pageable.pageSize).coerceAtMost(codeGroup.size)
        val subCodeGroup = codeGroup.subList(start, end)
        return PageImpl(subCodeGroup,pageable,codeGroup.size.toLong())
    }

    fun readCodeGroupByGroupId(id: Long): CodeGroupDetailResponseDto {
        // todo: CodeGroup 내 Algorithm List -> Page
        val codeGroup = codeGroupRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.CODE_GROUP_NOT_FOUND)
        return CodeGroupDetailResponseDto(codeGroup)
    }

    fun readAllOwnedCodeGroupByUserId(id: Long, pageable: Pageable): Page<SimpleCodeGroupResponseDto> {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val codeGroup = codeGroupRepository.findByOwner(user, pageable)
            .map { SimpleCodeGroupResponseDto(it) }
            .toList()

        return PageImpl(codeGroup, pageable, codeGroup.size.toLong())
    }

    fun importAlgorithmToCodeGroup(codeGroupId: Long, algorithmId: Long) {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        val codeGroup = codeGroupRepository.findByIdOrNull(codeGroupId) ?: throw CustomException(ErrorCode.CODE_GROUP_NOT_FOUND)

        if (user.id!! != codeGroup.owner.id!!) {
            throw CustomException(ErrorCode.NOT_YOUR_CODE_GROUP)
        }

        var algorithm = algorithmRepository.findByIdOrNull(algorithmId) ?: throw CustomException(ErrorCode.ALGORITHM_NOT_FOUND)

        if (algorithm.language != codeGroup.language) {
            throw CustomException(ErrorCode.LANGUAGE_MISMATCH)
        }

        algorithm.referencedCount++
        algorithm = algorithmRepository.save(algorithm)

        codeGroupAlgorithmRepository.save(CodeGroupAlgorithm(
            codeGroup = codeGroup,
            algorithm = algorithm
        ))
    }

    fun searchAllCodeGroups(request: CodeGroupSearchRequestDto, pageable: Pageable): Page<SimpleCodeGroupResponseDto> {
        // todo: Pageable 수정
        val language = if (request.language == null) {
            null
        } else {
            Language.valueOf(request.language)
        }
        val verified = request.verified
        val keyword = request.keyword
        val codeGroup = codeGroupRepository.findCodeGroupsByLanguageAndVerifiedAndKeyword(language, verified, keyword, pageable)

        return codeGroup.map { SimpleCodeGroupResponseDto(it) }
    }

}