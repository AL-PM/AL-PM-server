package com.alpm.server.domain.algorithm.service

import com.alpm.server.domain.algorithm.dao.AlgorithmRepository
import com.alpm.server.domain.algorithm.dto.request.AlgorithmCreateRequestDto
import com.alpm.server.domain.algorithm.dto.response.AlgorithmDetailResponseDto
import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.Language
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AlgorithmCreateService (

    private val algorithmRepository: AlgorithmRepository

) {

    fun saveAlgorithm(request: AlgorithmCreateRequestDto): AlgorithmDetailResponseDto {
        val user = SecurityContextHolder.getContext().authentication.principal as User

        val algorithm = algorithmRepository.save(
            Algorithm(
                name = request.name!!,
                language = Language.valueOf(request.language!!),
                content = request.content!!,
                description = request.description!!,
                owner = user
            )
        )

        return AlgorithmDetailResponseDto(algorithm)
    }

}