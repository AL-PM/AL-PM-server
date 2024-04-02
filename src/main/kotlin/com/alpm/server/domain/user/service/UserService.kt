package com.alpm.server.domain.user.service

import com.alpm.server.domain.history.dao.HistoryRepository
import com.alpm.server.domain.history.dto.HistoryDto
import com.alpm.server.domain.user.dao.UserRepository
import com.alpm.server.domain.user.dto.UserDto
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService (

    private val userRepository: UserRepository,

    private val historyRepository: HistoryRepository

) {

    fun loadUserById(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
    }

    fun readUserById(id: Long): UserDto {
        return UserDto(userRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.USER_NOT_FOUND))
    }

    fun readMyHistory(): List<HistoryDto> {
        val user = SecurityContextHolder.getContext().authentication.principal as User

        return historyRepository.findAllByUser(user).map { HistoryDto(it) }
    }

}