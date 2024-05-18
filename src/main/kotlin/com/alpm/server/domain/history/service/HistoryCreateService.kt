package com.alpm.server.domain.history.service

import com.alpm.server.domain.algorithm.dao.AlgorithmRepository
import com.alpm.server.domain.history.dao.HistoryRepository
import com.alpm.server.domain.history.dto.request.HistoryCreateRequestDto
import com.alpm.server.domain.history.dto.response.HistoryCreateResponseDto
import com.alpm.server.domain.history.entity.History
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class HistoryCreateService (

    private val historyRepository: HistoryRepository,

    private val algorithmRepository: AlgorithmRepository

){
    fun saveHistory(request: HistoryCreateRequestDto): HistoryCreateResponseDto {

        val user = SecurityContextHolder.getContext().authentication.principal as User

        val history = historyRepository.save(
            History(
                problemType = request.problemType,
                point = request.point,
                user = user,
                algorithm = algorithmRepository.findByIdOrNull(request.algorithmId)
                    ?:throw CustomException(ErrorCode.ALGORITHM_NOT_FOUND)
            )
        )
        return HistoryCreateResponseDto(history)
    }
}