package com.alpm.server.domain.codegroup.service

import com.alpm.server.domain.algorithm.dao.AlgorithmRepository
import com.alpm.server.domain.codegroup.dao.CodeGroupRepository
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.relation.dao.CodeGroupAlgorithmRepository
import com.alpm.server.global.common.relation.dao.UserCodeGroupRepository
import com.alpm.server.global.common.relation.entity.CodeGroupAlgorithm
import com.alpm.server.global.common.relation.entity.UserCodeGroup
import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class CodeGroupUpdateService (

    private val codeGroupRepository: CodeGroupRepository,

    private val algorithmRepository: AlgorithmRepository,

    private val userCodeGroupRepository: UserCodeGroupRepository,

    private val codeGroupAlgorithmRepository: CodeGroupAlgorithmRepository

) {

    fun importCodeGroupToUser(codeGroupId: Long) {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        var codeGroup = codeGroupRepository.findByIdOrNull(codeGroupId)
            ?: throw CustomException(ErrorCode.CODE_GROUP_NOT_FOUND)

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

    fun importAlgorithmToCodeGroup(codeGroupId: Long, algorithmId: Long) {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        val codeGroup = codeGroupRepository.findByIdOrNull(codeGroupId)
            ?: throw CustomException(ErrorCode.CODE_GROUP_NOT_FOUND)

        if (user.id!! != codeGroup.owner.id!!) {
            throw CustomException(ErrorCode.NOT_YOUR_CODE_GROUP)
        }

        var algorithm = algorithmRepository.findByIdOrNull(algorithmId)
            ?: throw CustomException(ErrorCode.ALGORITHM_NOT_FOUND)

        if (algorithm.language != codeGroup.language) {
            throw CustomException(ErrorCode.LANGUAGE_MISMATCH)
        }

        for (a in codeGroup.algorithmList.map { it.algorithm }) {
            if (a.id == algorithmId) {
                throw CustomException(ErrorCode.ALGORITHM_EXIST)
            }
        }

        algorithm.referencedCount++
        algorithm = algorithmRepository.save(algorithm)

        codeGroupAlgorithmRepository.save(
            CodeGroupAlgorithm(
                codeGroup = codeGroup,
                algorithm = algorithm
            )
        )
    }

}