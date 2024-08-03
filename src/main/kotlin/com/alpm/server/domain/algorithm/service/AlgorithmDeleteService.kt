package com.alpm.server.domain.algorithm.service

import com.alpm.server.domain.algorithm.dao.AlgorithmRepository
import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AlgorithmDeleteService(
    private val algorithmRepository: AlgorithmRepository
) {
    fun deleteAlgorithm(algorithmId:Long){
        var algorithm = algorithmRepository.findByIdOrNull(algorithmId)
            ?: throw CustomException(ErrorCode.ALGORITHM_NOT_FOUND)
        return algorithmRepository.deleteById(algorithmId)
    }
}