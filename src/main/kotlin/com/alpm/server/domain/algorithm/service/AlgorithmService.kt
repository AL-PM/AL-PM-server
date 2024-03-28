package com.alpm.server.domain.algorithm.service

import com.alpm.server.domain.algorithm.dao.AlgorithmRepository
import com.alpm.server.domain.algorithm.dto.request.AlgorithmCreateRequestDto
import com.alpm.server.domain.algorithm.dto.AlgorithmDto
import com.alpm.server.domain.algorithm.dto.SimpleAlgorithmDto
import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.user.dao.UserRepository
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.Language
import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service


@Service
class AlgorithmService(

    private val algorithmRepository: AlgorithmRepository,

    private val userRepository: UserRepository

){

    fun saveAlgorithm(request: AlgorithmCreateRequestDto): AlgorithmDto {
        val user = SecurityContextHolder.getContext().authentication.principal as User

        val algorithm = algorithmRepository.save(Algorithm(
            name = request.name!!,
            language = Language.valueOf(request.language!!),
            content = request.content!!,
            description = request.description!!,
            owner = user
        ))

        return AlgorithmDto(algorithm)
    }

    fun readAlgorithmById(id: Long): AlgorithmDto {
        val algorithm = algorithmRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.ALGORITHM_NOT_FOUND)

        return AlgorithmDto(algorithm)
    }

    fun readAlgorithmByIdForAnonymous(id: Long): AlgorithmDto {
        val algorithm = algorithmRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.ALGORITHM_NOT_FOUND)

        if (!algorithm.verified) {
            throw CustomException(ErrorCode.ANONYMOUS)
        }

        return AlgorithmDto(algorithm)
    }

    fun readAllAlgorithms(): List<SimpleAlgorithmDto> {
        return algorithmRepository.findAll().map { SimpleAlgorithmDto(it) }
    }

    fun readAllAlgorithmsByUserId(id: Long): List<SimpleAlgorithmDto> {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        return user.myAlgorithms.map { SimpleAlgorithmDto(it) }
    }

    fun readAllOwnedAlgorithmsByUserId(id:Long): List<SimpleAlgorithmDto> {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        return user.ownedAlgorithmList.map { SimpleAlgorithmDto(it) }
    }
}
