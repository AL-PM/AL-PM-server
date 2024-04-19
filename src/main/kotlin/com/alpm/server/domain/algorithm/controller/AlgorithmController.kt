package com.alpm.server.domain.algorithm.controller

import com.alpm.server.domain.algorithm.dto.request.AlgorithmCreateRequestDto
import com.alpm.server.domain.algorithm.dto.request.AlgorithmSearchRequestDto
import com.alpm.server.domain.algorithm.dto.response.AlgorithmDetailResponseDto
import com.alpm.server.domain.algorithm.dto.response.SimpleAlgorithmResponseDto
import com.alpm.server.domain.algorithm.service.AlgorithmService
import com.alpm.server.domain.codegroup.entity.CodeGroup
import com.alpm.server.global.validation.ValidationSequence
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/algorithm")
class AlgorithmController(

    private val algorithmService: AlgorithmService

) {

    @Operation(summary = "Algorithm 생성")
    @PostMapping("/create")
    fun createAlgorithm(
        @RequestBody @Validated(value = [ValidationSequence::class]) request: AlgorithmCreateRequestDto
    ): ResponseEntity<AlgorithmDetailResponseDto> {
        return ResponseEntity.ok().body(algorithmService.saveAlgorithm(request))
    }

    @Operation(summary = "Algorithm 단일 조회")
    @GetMapping("/{id}")
    fun readAlgorithm(@PathVariable("id") id: Long): ResponseEntity<AlgorithmDetailResponseDto> {
        return ResponseEntity.ok().body(algorithmService.readAlgorithmById(id))
    }

    @Operation(summary = "비회원용 Algorithm 단일 조회")
    @GetMapping("/anonymous/{id}")
    fun readAlgorithmForAnonymous(@PathVariable("id") id: Long): ResponseEntity<AlgorithmDetailResponseDto> {
        return ResponseEntity.ok().body(algorithmService.readAlgorithmByIdForAnonymous(id))
    }

    @Operation(summary = "Algorithm 전체 조회")
    @GetMapping("/")
    fun readAllAlgorithms(pageable: Pageable): ResponseEntity<Page<SimpleAlgorithmResponseDto>> {
        return ResponseEntity.ok().body(algorithmService.readAllAlgorithms(pageable))
    }

    @Operation(summary = "특정 유저의 Algorithm 전체 조회")
    @GetMapping("/user/{id}")
    fun readAllAlgorithmsByUserId(@PathVariable("id") id: Long,pageable: Pageable): ResponseEntity<Page<CodeGroup>> {
        return ResponseEntity.ok().body(algorithmService.readAllAlgorithmsByUserId(id,pageable))
    }

    @Operation(summary = "특정 User(Owner)가 작성한 Algorithm 전체 조회")
    @GetMapping("/owner/{id}")
    fun readAllOwnedAlgorithmsByUserId(
        @PathVariable("id") id: Long, pageable: Pageable
    ): ResponseEntity<Page<SimpleAlgorithmResponseDto>> {
        return ResponseEntity.ok().body(algorithmService.readAllOwnedAlgorithmsByUserId(id,pageable))
    }

    @Operation(summary = "Algorithm 검색")
    @GetMapping("/search")
    fun searchAllAlgorithm(
        @ModelAttribute @Validated(value = [ValidationSequence::class]) request: AlgorithmSearchRequestDto, pageable: Pageable
    ): ResponseEntity<Page<SimpleAlgorithmResponseDto>> {
        return ResponseEntity.ok().body(algorithmService.searchAllAlgorithms(request,pageable))
    }

}