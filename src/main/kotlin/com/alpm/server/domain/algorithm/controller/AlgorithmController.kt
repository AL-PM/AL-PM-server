package com.alpm.server.domain.algorithm.controller

import com.alpm.server.domain.algorithm.dto.request.AlgorithmCreateRequestDto
import com.alpm.server.domain.algorithm.dto.AlgorithmDto
import com.alpm.server.domain.algorithm.dto.SimpleAlgorithmDto
import com.alpm.server.domain.algorithm.service.AlgorithmService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/algorithm")
class AlgorithmController(

    private val algorithmService: AlgorithmService

) {

    @Operation(summary = "Algorithm 생성")
    @PostMapping("/create")
    fun createAlgorithm(@RequestBody request: AlgorithmCreateRequestDto): ResponseEntity<AlgorithmDto> {
        return ResponseEntity.ok().body(algorithmService.saveAlgorithm(request))
    }

    @Operation(summary = "Algorithm 단일 조회")
    @GetMapping("/{id}")
    fun readAlgorithm(@PathVariable("id") id: Long): ResponseEntity<AlgorithmDto> {
        return ResponseEntity.ok().body(algorithmService.readAlgorithmById(id))
    }

    @Operation(summary = "비회원용 Algorithm 단일 조회")
    @GetMapping("/anonymous/{id}")
    fun readAlgorithmForAnonymous(@PathVariable("id") id: Long): ResponseEntity<AlgorithmDto> {
        return ResponseEntity.ok().body(algorithmService.readAlgorithmByIdForAnonymous(id))
    }

    @Operation(summary = "Algorithm 전체 조회")
    @GetMapping("/")
    fun readAllAlgorithms(): ResponseEntity<List<SimpleAlgorithmDto>> {
        return ResponseEntity.ok().body(algorithmService.readAllAlgorithms())
    }

    @Operation(summary = "특정 유저의 Algorithm 전체 조회")
    @GetMapping("/user/{id}")
    fun readAllAlgorithmsByUserId(@PathVariable("id") id: Long): ResponseEntity<List<SimpleAlgorithmDto>> {
        return ResponseEntity.ok().body(algorithmService.readAllAlgorithmsByUserId(id))
    }

    @Operation(summary = "특정 User(Owner)가 작성한 Algorithm 전체 조회")
    @GetMapping("/owner/{id}")
    fun readAllOwnedAlgorithmsByUserId(@PathVariable("id") id: Long): ResponseEntity<List<SimpleAlgorithmDto>> {
        return ResponseEntity.ok().body(algorithmService.readAllOwnedAlgorithmsByUserId(id))
    }

}