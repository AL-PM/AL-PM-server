package com.alpm.server.domain.user.service

import com.alpm.server.domain.history.dao.HistoryRepository
import com.alpm.server.domain.history.dto.response.HistorySizeDto
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
import java.time.temporal.ChronoUnit

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

        val thisYear = LocalDateTime.now().year
        var newYear = LocalDate.of(thisYear, 1, 1)

        val historySizeList = ArrayList<HistorySizeDto>()
        while (newYear.year == thisYear) {
            historySizeList.add(HistorySizeDto(newYear, 0))
            newYear = newYear.plusDays(1)
        }

        val startDateTime = LocalDateTime.of(LocalDate.of(LocalDate.now().year, 1, 1), LocalTime.of(0, 0, 0))
        val endDateTime = LocalDateTime.now()
        historyRepository.findAllByUserAndCreatedAtBetween(user, startDateTime, endDateTime)
            .fold(historySizeList) { list, history ->
                list.apply {
                    val days = LocalDate.of(thisYear, 1, 1).until(history.createdAt.toLocalDate(), ChronoUnit.DAYS)
                    list[days.toInt()].size++
                }
            }

        return UserWithHistoryResponseDto(user, historySizeList)
    }

}