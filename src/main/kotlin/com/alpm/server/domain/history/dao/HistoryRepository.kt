package com.alpm.server.domain.history.dao

import com.alpm.server.domain.history.entity.History
import com.alpm.server.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface HistoryRepository: JpaRepository<History, Long> {

    fun findAllByUser(user: User): List<History>

}