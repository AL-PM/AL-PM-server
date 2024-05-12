package com.alpm.server.domain.history.dto.response

import java.time.LocalDate

data class HistorySizeDto (

    val date: LocalDate,

    var size: Int

) {
}