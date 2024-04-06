package com.alpm.server.domain.user.service

import com.alpm.server.domain.history.dao.HistoryRepository
import com.alpm.server.domain.history.dto.response.HistoryDetailResponseDto
import com.alpm.server.domain.user.dao.UserRepository
import com.alpm.server.domain.user.dto.response.SimpleUserResponseDto
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService (

    private val userRepository: UserRepository

) {

    fun loadUserById(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
    }

    fun readUserById(id: Long): SimpleUserResponseDto {
        return SimpleUserResponseDto(
            userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        )
    }

    fun readUserHistory(id: Long): List<HistoryDetailResponseDto> {
        val user = userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return user.historyList.map { HistoryDetailResponseDto(it) }
    }

}