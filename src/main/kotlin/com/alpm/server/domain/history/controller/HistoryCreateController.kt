package com.alpm.server.domain.history.controller

import com.alpm.server.domain.history.dto.request.HistoryCreateRequestDto
import com.alpm.server.domain.history.dto.response.HistoryCreateResponseDto
import com.alpm.server.domain.history.service.HistoryCreateService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "History Create", description = "History 생성 API")
@RestController
@RequestMapping("/history")
class HistoryCreateController (
    private val historyCreateService: HistoryCreateService
){
    @Operation(summary = "히스토리 생성")
    @PostMapping("/create")
    fun createHistory(
        @RequestBody request: HistoryCreateRequestDto
    ): ResponseEntity<HistoryCreateResponseDto> {
        return ResponseEntity.ok().body(historyCreateService.saveHistory(request))
    }
}