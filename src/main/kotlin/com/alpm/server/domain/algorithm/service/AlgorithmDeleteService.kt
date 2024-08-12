package com.alpm.server.domain.algorithm.service

import com.alpm.server.domain.algorithm.dao.AlgorithmRepository
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AlgorithmDeleteService(
    private val algorithmRepository: AlgorithmRepository
) {
    fun deleteAlgorithm(algorithmId:Long){
        val user = SecurityContextHolder.getContext().authentication.principal as User
        var algorithm = algorithmRepository.findByIdOrNull(algorithmId)
            ?: throw CustomException(ErrorCode.ALGORITHM_NOT_FOUND)
        if(algorithm.owner.id != user.id) throw CustomException(ErrorCode.NO_GRANT)

        return algorithmRepository.deleteById(algorithmId)
    }
}