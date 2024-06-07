package com.alpm.server.domain.algorithm.service

import com.alpm.server.domain.algorithm.dao.AlgorithmRepository
import com.alpm.server.domain.algorithm.dto.request.AlgorithmSearchRequestDto
import com.alpm.server.domain.algorithm.dto.response.AlgorithmDetailResponseDto
import com.alpm.server.domain.algorithm.dto.response.SimpleAlgorithmResponseDto
import com.alpm.server.domain.codegroup.dao.CodeGroupRepository
import com.alpm.server.domain.user.dao.UserRepository
import com.alpm.server.global.common.model.Language
import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class AlgorithmReadService(

    private val algorithmRepository: AlgorithmRepository,

    private val userRepository: UserRepository,

    private val codeGroupRepository: CodeGroupRepository

){

    fun readAlgorithmById(algorithmId: Long): AlgorithmDetailResponseDto {
        val algorithm = algorithmRepository.findByIdOrNull(algorithmId)
            ?: throw CustomException(ErrorCode.ALGORITHM_NOT_FOUND)

        return AlgorithmDetailResponseDto(algorithm)
    }

    fun readAlgorithmByIdForAnonymous(algorithmId: Long): AlgorithmDetailResponseDto {
        val algorithm = algorithmRepository.findByIdOrNull(algorithmId)
            ?: throw CustomException(ErrorCode.ALGORITHM_NOT_FOUND)

        if (!algorithm.verified) {
            throw CustomException(ErrorCode.ANONYMOUS)
        }

        return AlgorithmDetailResponseDto(algorithm)
    }

    fun readAlgorithms(pageable: Pageable): Page<SimpleAlgorithmResponseDto> {
        return algorithmRepository.findAll(pageable).map { SimpleAlgorithmResponseDto(it) }
    }

    fun readAlgorithmsByUser(userId: Long, pageable: Pageable): Page<SimpleAlgorithmResponseDto> {
        val user = userRepository.findByIdOrNull(userId) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val algorithmList = algorithmRepository.findAlgorithmsByUser(user, pageable)

        return algorithmList.map { SimpleAlgorithmResponseDto(it) }
    }

    fun readAlgorithmsByOwner(ownerId: Long, pageable: Pageable): Page<SimpleAlgorithmResponseDto> {
        val user = userRepository.findByIdOrNull(ownerId) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val algorithmList = algorithmRepository.findAlgorithmsByOwner(user, pageable)

        return algorithmList.map { SimpleAlgorithmResponseDto(it) }
    }

    fun readAlgorithmsByCodeGroup(codeGroupId: Long, pageable: Pageable): Page<SimpleAlgorithmResponseDto> {
        val codeGroup = codeGroupRepository.findByIdOrNull(codeGroupId)
            ?: throw CustomException(ErrorCode.CODE_GROUP_NOT_FOUND)
        val algorithmList = algorithmRepository.findAlgorithmsByCodeGroup(codeGroup, pageable)

        return algorithmList.map { SimpleAlgorithmResponseDto(it) }
    }

    fun searchAlgorithms(request: AlgorithmSearchRequestDto, pageable: Pageable): Page<SimpleAlgorithmResponseDto> {
        val algorithmList = algorithmRepository.findAlgorithmsByLanguageAndVerifiedAndKeyword(
            language = if (request.language == null) {
                null
            } else {
                // Language enum 에 없는 경우는 request dto 의 enum 어노테이션에서 검사
                Language.valueOf(request.language)
            },
            verified = request.verified,
            keyword = request.keyword,
            pageable = pageable
        )

        return algorithmList.map { SimpleAlgorithmResponseDto(it) }
    }

    fun readRandomAlgorithmByCodeGroupId(codeGroupId: Long): AlgorithmDetailResponseDto {
        val codeGroup = codeGroupRepository.findByIdOrNull(codeGroupId)
            ?: throw CustomException(ErrorCode.CODE_GROUP_NOT_FOUND)

        val pick = codeGroup.algorithmList.random()

        return AlgorithmDetailResponseDto(pick.algorithm)
    }

}
