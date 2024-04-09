package com.alpm.server.domain.history.dao

import com.alpm.server.domain.history.entity.History
import com.alpm.server.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface HistoryRepository: JpaRepository<History, Long> {

    @Query("SELECT h FROM History h " +
            "WHERE (h.user = :user) AND (h.createdAt BETWEEN :startDateTime AND :endDateTime) " +
            "ORDER BY h.createdAt")
    fun findAllByUserAndCreatedAtBetween(user: User, startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<History>

}