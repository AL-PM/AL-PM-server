package com.alpm.server.domain.codegroup.controller


import com.alpm.server.domain.codegroup.dto.request.CodeGroupCreateRequestDto
import com.alpm.server.domain.codegroup.dto.request.CodeGroupSearchRequestDto
import com.alpm.server.domain.codegroup.dto.response.CodeGroupDetailResponseDto
import com.alpm.server.domain.codegroup.dto.response.SimpleCodeGroupResponseDto
import com.alpm.server.domain.codegroup.service.CodeGroupService
import com.alpm.server.global.validation.ValidationSequence
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/codegroup")
class CodeGroupController(

    private val codeGroupService: CodeGroupService

) {
    @Operation(summary = "CodeGroup 생성")
    @PostMapping("/create")
    fun createCodeGroup(
        @RequestBody @Validated(value = [ValidationSequence::class]) request: CodeGroupCreateRequestDto
    ): ResponseEntity<CodeGroupDetailResponseDto> {
        return ResponseEntity.ok().body(codeGroupService.saveCodeGroup(request))
    }

    @Operation(summary = "CodeGroup 가져오기")
    @PatchMapping("/import/{id}")
    fun importCodeGroupById(@PathVariable("id") id: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok(codeGroupService.importCodeGroupById(id))
    }

    @Operation(summary = "CodeGroup 전체 조회")
    @GetMapping("")
    fun readAllCodeGroups(pageable: Pageable): ResponseEntity<Page<SimpleCodeGroupResponseDto>> {
        return ResponseEntity.ok().body(codeGroupService.readAllCodeGroups(pageable))
    }

    @Operation(summary = "특정 User의 CodeGroup 전체 조회")
    @GetMapping("/user/{id}")
    fun readCodeGroupsByUserId(@PathVariable("id") id: Long, pageable: Pageable): ResponseEntity<Page<SimpleCodeGroupResponseDto>> {
        return ResponseEntity.ok().body(codeGroupService.readCodeGroupsByUserId(id,pageable))
    }

    @Operation(summary = "CodeGroup 단일 조회")
    @GetMapping("/{id}")
    fun readCodeGroupById(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return ResponseEntity.ok().body(codeGroupService.readAllCodeGroupByGroupId(id))
    }

    @Operation(summary = "특정 User(Owner)가 작성한 CodeGroup 전체 조회")
    @GetMapping("/owner/{id}")
    fun readAllOwnedCodeGroupByUserId(@PathVariable("id") id: Long,pageable: Pageable): ResponseEntity<Page<SimpleCodeGroupResponseDto>> {
        return ResponseEntity.ok().body(codeGroupService.readAllOwnedCodeGroupByUserId(id,pageable))
    }

    @Operation(summary = "CodeGroup에 Algorithm 추가")
    @PatchMapping("/{codeGroupId}/algorithm/{algorithmId}")
    fun importAlgorithmToCodeGroup(
        @PathVariable("codeGroupId") codeGroupId: Long,
        @PathVariable("algorithmId") algorithmId: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(codeGroupService.importAlgorithmToCodeGroup(codeGroupId, algorithmId))
    }

    @Operation(summary = "CodeGroup 검색")
    @GetMapping("/search")
    fun searchAllAlgorithm(
        @ModelAttribute @Validated(value = [ValidationSequence::class]) request: CodeGroupSearchRequestDto,
        pageable: Pageable
    ): ResponseEntity<Page<SimpleCodeGroupResponseDto>> {
        return ResponseEntity.ok().body(codeGroupService.searchAllCodeGroups(request,pageable))
    }

}