package com.alpm.server.domain.algorithm.controller

import com.alpm.server.domain.algorithm.service.AlgorithmDeleteService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Algorithm Delete", description = "Algorithm 제거 API")
@RestController
@RequestMapping("/algorithm")
class AlgorithmDeleteController (

    private val algorithmDeleteService: AlgorithmDeleteService

) {
    @Operation(summary = "Algorithm 삭제")
    @DeleteMapping("/delete/{algorithmId}")
    fun deleteAlgorithm(
        @PathVariable("algorithmId") algorithmId: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(algorithmDeleteService.deleteAlgorithm(algorithmId))
    }
}