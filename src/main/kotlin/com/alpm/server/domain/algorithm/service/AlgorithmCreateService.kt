package com.alpm.server.domain.algorithm.service

import com.alpm.server.domain.algorithm.dao.AlgorithmRepository
import com.alpm.server.domain.algorithm.dto.request.AlgorithmCreateRequestDto
import com.alpm.server.domain.algorithm.dto.response.AlgorithmDetailResponseDto
import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.Language
import com.alpm.server.infra.openai.OpenAiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AlgorithmCreateService (

    private val algorithmRepository: AlgorithmRepository,

    private val openAiService: OpenAiService

) {

    fun test(code: String): String? {
        val content = openAiService.organizeAndAnnotate(code)
        return openAiService.generateBlanks(content)
    }

    fun saveAlgorithm(request: AlgorithmCreateRequestDto): AlgorithmDetailResponseDto {
        val user = SecurityContextHolder.getContext().authentication.principal as User

        // todo: original에 request.content를 chatGPT를 통해 코드 형식을 통일하여 넣어야 함
        // todo: content에 original을 chatGPT를 통해 가공하여 넣어야 함

        val algorithm = algorithmRepository.save(
            Algorithm(
                name = request.name!!,
                language = Language.valueOf(request.language!!),
                original = request.content!!,
                content = request.content,
                description = request.description!!,
                owner = user
            )
        )

        return AlgorithmDetailResponseDto(algorithm)
    }

}