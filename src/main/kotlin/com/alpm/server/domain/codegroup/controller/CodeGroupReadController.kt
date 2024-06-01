package com.alpm.server.domain.codegroup.controller

import com.alpm.server.domain.algorithm.dto.response.AlgorithmDetailResponseDto
import com.alpm.server.domain.codegroup.dto.request.CodeGroupSearchRequestDto
import com.alpm.server.domain.codegroup.dto.response.SimpleCodeGroupResponseDto
import com.alpm.server.domain.codegroup.service.CodeGroupReadService
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

@Tag(name = "CodeGroup Read", description = "CodeGroup 조회 API")
@RestController
@RequestMapping("/codeGroup")
class CodeGroupReadController(

    private val codeGroupReadService: CodeGroupReadService

) {

    @Operation(summary = "CodeGroup 단일 조회")
    @GetMapping("/{codeGroupId}")
    fun readCodeGroupsById(@PathVariable("codeGroupId") codeGroupId: Long): ResponseEntity<SimpleCodeGroupResponseDto> {
        return ResponseEntity.ok().body(codeGroupReadService.readCodeGroupById(codeGroupId))
    }

    @Operation(summary = "CodeGroup 전체 조회")
    @GetMapping("")
    fun readCodeGroups(
        @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<SimpleCodeGroupResponseDto>> {
        return ResponseEntity.ok().body(codeGroupReadService.readCodeGroups(pageable))
    }

    @Operation(summary = "특정 User의 CodeGroup 전체 조회")
    @GetMapping("/user/{userId}")
    fun readCodeGroupsByUser(
        @PathVariable("userId") userId: Long,
        @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<SimpleCodeGroupResponseDto>> {
        return ResponseEntity.ok().body(codeGroupReadService.readCodeGroupsByUser(userId, pageable))
    }

    @Operation(summary = "특정 User(Owner)가 작성한 CodeGroup 전체 조회")
    @GetMapping("/owner/{ownerId}")
    fun readCodeGroupsByOwner(
        @PathVariable("ownerId") ownerId: Long,
        @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<SimpleCodeGroupResponseDto>> {
        return ResponseEntity.ok().body(codeGroupReadService.readCodeGroupsByOwner(ownerId, pageable))
    }

    @Operation(summary = "CodeGroup 검색")
    @GetMapping("/search")
    fun searchCodeGroups(
        @ModelAttribute @Validated(value = [ValidationSequence::class]) request: CodeGroupSearchRequestDto,
        @PageableDefault(page = 0, size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<SimpleCodeGroupResponseDto>> {
        return ResponseEntity.ok().body(codeGroupReadService.searchCodeGroups(request,pageable))
    }

    @Operation(summary = "CodeGroup 내의 Algorithm 중 랜덤으로 단일 조회")
    @GetMapping("/{codeGroupId}/random")
    fun readRandomAlgorithmByCodeGroupId(@PathVariable codeGroupId: Long): ResponseEntity<AlgorithmDetailResponseDto> {
        return ResponseEntity.ok().body(codeGroupReadService.readRandomAlgorithmByCodeGroupId(codeGroupId))
    }

}