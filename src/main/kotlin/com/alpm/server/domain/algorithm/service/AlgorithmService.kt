package com.alpm.server.domain.algorithm.service

import com.alpm.server.domain.algorithm.dao.AlgorithmRepository
import com.alpm.server.domain.algorithm.dto.AlgorithmCreateRequestDto
import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.global.common.model.Language
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

data class AlgorithmRequest(
        val name: String,
        val language: Language,
        val content: String,
        val description: String,
)
@Service
class AlgorithmService(
        private val algorithmRepository: AlgorithmRepository,
        val objectMapper: ObjectMapper
){
    //name:String, language: Language, content:String, description:String

    fun deleteAlgorithm(id: Long){
        algorithmRepository.deleteById(id)
    }

    fun saveAlgorithm(request: AlgorithmCreateRequestDto): Algorithm {
        val algorithm = Algorithm(
                name = request.name,
                language = request.language,
                content = request.content,
                description = request.description
        )
        return algorithmRepository.save(algorithm)
    }
    // id = ?
}
