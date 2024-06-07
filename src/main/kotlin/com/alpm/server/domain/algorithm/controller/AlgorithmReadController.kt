package com.alpm.server.domain.algorithm.controller

import com.alpm.server.domain.algorithm.dto.request.AlgorithmSearchRequestDto
import com.alpm.server.domain.algorithm.dto.response.AlgorithmDetailResponseDto
import com.alpm.server.domain.algorithm.dto.response.SimpleAlgorithmResponseDto
import com.alpm.server.domain.algorithm.service.AlgorithmReadService
import com.alpm.server.global.validation.ValidationSequence
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Tag(name = "Algorithm Read", description = "Algorithm 조회 API")
@RestController
@RequestMapping("/algorithm")
class AlgorithmReadController (

    private val algorithmReadService: AlgorithmReadService

) {

    @Operation(summary = "Algorithm 단일 조회")
    @GetMapping("/{algorithmId}")
    fun readAlgorithm(
        @PathVariable("algorithmId") algorithmId: Long
    ): ResponseEntity<AlgorithmDetailResponseDto> {
        return ResponseEntity.ok().body(algorithmReadService.readAlgorithmById(algorithmId))
    }

    @Operation(summary = "비회원용 Algorithm 단일 조회")
    @GetMapping("/anonymous/{algorithmId}")
    fun readAlgorithmForAnonymous(
        @PathVariable("algorithmId") algorithmId: Long
    ): ResponseEntity<AlgorithmDetailResponseDto> {
        return ResponseEntity.ok().body(algorithmReadService.readAlgorithmByIdForAnonymous(algorithmId))
    }

    @Operation(summary = "Algorithm 전체 조회")
    @GetMapping("/")
    fun readAlgorithms(
        @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<SimpleAlgorithmResponseDto>> {
        return ResponseEntity.ok().body(algorithmReadService.readAlgorithms(pageable))
    }

    @Operation(summary = "특정 User의 Algorithm 전체 조회")
    @GetMapping("/user/{userId}")
    fun readAlgorithmsByUser(
        @PathVariable("userId") userId: Long,
        @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<SimpleAlgorithmResponseDto>> {
        return ResponseEntity.ok().body(algorithmReadService.readAlgorithmsByUser(userId, pageable))
    }

    @Operation(summary = "특정 User(Owner)가 작성한 Algorithm 전체 조회")
    @GetMapping("/owner/{ownerId}")
    fun readAlgorithmsByOwner(
        @PathVariable("ownerId") ownerId: Long,
        @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<SimpleAlgorithmResponseDto>> {
        return ResponseEntity.ok().body(
            algorithmReadService.readAlgorithmsByOwner(ownerId, pageable)
        )
    }

    @Operation(summary = "CodeGroup 내 Algorithm 조회")
    @GetMapping("/codeGroup/{codeGroupId}")
    fun readAlgorithmsByCodeGroup(
        @PathVariable("codeGroupId") codeGroupId: Long,
        @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<SimpleAlgorithmResponseDto>> {
        return ResponseEntity.ok().body(
            algorithmReadService.readAlgorithmsByCodeGroup(codeGroupId,pageable)
        )
    }

    @Operation(summary = "Algorithm 검색")
    @GetMapping("/search")
    fun searchAlgorithms(
        @ModelAttribute @Validated(value = [ValidationSequence::class]) request: AlgorithmSearchRequestDto,
        @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<SimpleAlgorithmResponseDto>> {
        return ResponseEntity.ok().body(algorithmReadService.searchAlgorithms(request,pageable))
    }

    @Operation(summary = "CodeGroup 내의 Algorithm 중 랜덤으로 단일 조회")
    @GetMapping("/random/codeGroup/{codeGroupId}")
    fun readRandomAlgorithmByCodeGroupId(@PathVariable codeGroupId: Long): ResponseEntity<AlgorithmDetailResponseDto> {
        return ResponseEntity.ok().body(algorithmReadService.readRandomAlgorithmByCodeGroupId(codeGroupId))
    }

}