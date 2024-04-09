package com.alpm.server.domain.algorithm.service

import com.alpm.server.domain.algorithm.dao.AlgorithmRepository
import com.alpm.server.domain.algorithm.dto.request.AlgorithmCreateRequestDto
import com.alpm.server.domain.algorithm.dto.request.AlgorithmSearchRequestDto
import com.alpm.server.domain.algorithm.dto.response.AlgorithmDetailResponseDto
import com.alpm.server.domain.algorithm.dto.response.SimpleAlgorithmResponseDto
import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.user.dao.UserRepository
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.Language
import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import org.hibernate.query.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service


@Service
class AlgorithmService(

    private val algorithmRepository: AlgorithmRepository,

    private val userRepository: UserRepository

){

    fun saveAlgorithm(request: AlgorithmCreateRequestDto): AlgorithmDetailResponseDto {
        val user = SecurityContextHolder.getContext().authentication.principal as User

        val algorithm = algorithmRepository.save(Algorithm(
            name = request.name!!,
            language = Language.valueOf(request.language!!),
            content = request.content!!,
            description = request.description!!,
            owner = user
        ))

        return AlgorithmDetailResponseDto(algorithm)
    }

    fun readAlgorithmById(id: Long): AlgorithmDetailResponseDto {
        val algorithm = algorithmRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.ALGORITHM_NOT_FOUND)

        return AlgorithmDetailResponseDto(algorithm)
    }

    fun readAlgorithmByIdForAnonymous(id: Long): AlgorithmDetailResponseDto {
        val algorithm = algorithmRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.ALGORITHM_NOT_FOUND)

        if (!algorithm.verified) {
            throw CustomException(ErrorCode.ANONYMOUS)
        }

        return AlgorithmDetailResponseDto(algorithm)
    }

    fun readAllAlgorithms(pageable:Pageable): org.springframework.data.domain.Page<SimpleAlgorithmResponseDto> {
        return algorithmRepository.findAll(pageable).map { SimpleAlgorithmResponseDto(it) }
    }

    fun readAllAlgorithmsByUserId(id: Long): List<SimpleAlgorithmResponseDto> {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val codeGroupList = user.codeGroupList
            .map {
                it.codeGroup
            }
            .filter {
                it.visible || it.owner.id!! == user.id!!
            }

        val set = HashSet<Algorithm>()
        for (codeGroup in codeGroupList) {
            set.addAll(codeGroup.algorithmList.map { it.algorithm })
        }

        return set.map { SimpleAlgorithmResponseDto(it) }
    }

    fun readAllOwnedAlgorithmsByUserId(id:Long): List<SimpleAlgorithmResponseDto> {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        return user.ownedAlgorithmList.map { SimpleAlgorithmResponseDto(it) }
    }

    fun searchAllAlgorithms(request: AlgorithmSearchRequestDto): List<SimpleAlgorithmResponseDto> {
        val language = if (request.language == null) {
            null
        } else {
            // Language enum 에 없는 경우는 request dto 의 enum 어노테이션에서 검사
            Language.valueOf(request.language)
        }
        val verified = request.verified
        val keyword = request.keyword
        return algorithmRepository.findAlgorithmsByLanguageAndVerifiedAndKeyword(language, verified, keyword)
            .map { SimpleAlgorithmResponseDto(it) }
    }

}
