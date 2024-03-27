package com.alpm.server.domain.history.dao

import com.alpm.server.domain.history.entity.History
import org.springframework.data.jpa.repository.JpaRepository

interface HistoryRepository: JpaRepository<History, Long> {
}