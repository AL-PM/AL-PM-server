package com.alpm.server.domain.history.service

import com.alpm.server.domain.history.dao.HistoryRepository
import com.alpm.server.domain.history.dto.request.HistoryCreateRequestDto
import com.alpm.server.domain.history.dto.response.HistoryCreateResponseDto
import com.alpm.server.domain.history.entity.History
import org.springframework.stereotype.Service

@Service
class HistoryCreateService (
    private val historyRepository: HistoryRepository
){
    fun saveHistory(request: HistoryCreateRequestDto): HistoryCreateResponseDto {

        val history = historyRepository.save(
            History(
                problemType = request.problemType,
                point = request.point,
                user = request.user,
                algorithm = request.algorithm
            )
        )
        return HistoryCreateResponseDto(history)
    }
}