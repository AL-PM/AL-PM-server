package com.alpm.server.domain.user.service

import com.alpm.server.domain.history.dao.HistoryRepository
import com.alpm.server.domain.history.dto.response.HistoryDetailResponseDto
import com.alpm.server.domain.user.dao.UserRepository
import com.alpm.server.domain.user.dto.response.UserWithHistoryResponseDto
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class UserService (

    private val userRepository: UserRepository,

    private val historyRepository: HistoryRepository

) {

    fun loadUserById(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
    }

    fun readUserById(id: Long): UserWithHistoryResponseDto {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        val startDateTime = LocalDateTime.of(LocalDate.now().minusDays(84), LocalTime.of(0, 0, 0))
        val endDateTime = LocalDateTime.now()
        val historyList = historyRepository.findAllByUserAndCreatedAtBetween(user, startDateTime, endDateTime)
            .fold(ArrayList<ArrayList<HistoryDetailResponseDto>>()) { list, history ->
                list.apply {
                    if (isEmpty() || last().last().createdAt.toLocalDate() != history.createdAt.toLocalDate()) {
                        add(arrayListOf(HistoryDetailResponseDto(history)))
                    } else {
                        last().add(HistoryDetailResponseDto(history))
                    }
                }
            }

        return UserWithHistoryResponseDto(user, historyList)
    }

}