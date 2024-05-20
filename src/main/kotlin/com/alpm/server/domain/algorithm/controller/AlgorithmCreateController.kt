package com.alpm.server.domain.algorithm.controller

import com.alpm.server.domain.algorithm.dto.request.AlgorithmCreateRequestDto
import com.alpm.server.domain.algorithm.dto.response.AlgorithmDetailResponseDto
import com.alpm.server.domain.algorithm.service.AlgorithmCreateService
import com.alpm.server.global.validation.ValidationSequence
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Tag(name = "Algorithm Create", description = "Algorithm 생성 API")
@RestController
@RequestMapping("/algorithm")
class AlgorithmCreateController(

    private val algorithmCreateService: AlgorithmCreateService

) {

    @Operation(summary = "Algorithm 생성")
    @PostMapping("/create")
    fun createAlgorithm(
        @RequestBody @Validated(value = [ValidationSequence::class]) request: AlgorithmCreateRequestDto
    ): ResponseEntity<AlgorithmDetailResponseDto> {
        return ResponseEntity.ok().body(algorithmCreateService.saveAlgorithm(request))
    }

}