package com.alpm.server.domain.codegroup.controller


import com.alpm.server.domain.codegroup.dto.*
import com.alpm.server.domain.codegroup.dto.request.CodeGroupCreateRequestDto
import com.alpm.server.domain.codegroup.dto.response.CodeGroupListResponseDto
import com.alpm.server.domain.codegroup.service.CodeGroupService
import com.alpm.server.global.validation.ValidationSequence
import io.swagger.v3.oas.annotations.Operation
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
    ): ResponseEntity<CodeGroupDto> {
        return ResponseEntity.ok().body(codeGroupService.saveCodeGroup(request))
    }

    @Operation(summary = "CodeGroup 가져오기")
    @PutMapping("/import/{id}")
    fun putCodeGroup(@PathVariable("id") id: Long): ResponseEntity<CodeGroupDto> {
        return ResponseEntity.ok(codeGroupService.putCodeGroupByID(id))
    }

    @Operation(summary = "CodeGroup 전체 조회")
    @GetMapping("")
    fun readAllCodeGroups(): ResponseEntity<List<CodeGroupListResponseDto>> {
        return ResponseEntity.ok().body(codeGroupService.readAllCodeGroups())
    }

    @Operation(summary = "특정 User의 CodeGroup 전체 조회")
    @GetMapping("/user/{id}")
    fun readCodeGroupsByUserId(@PathVariable("id") id : Long): ResponseEntity<List<CodeGroupListResponseDto>> {
        return ResponseEntity.ok().body(codeGroupService.readCodeGroupsByUserId(id))
    }

    @Operation(summary = "CodeGroup 단일 조회")
    @GetMapping("/{id}")
    fun readCodeGroupById(@PathVariable("id") id : Long): ResponseEntity<Any> {
        return ResponseEntity.ok().body(codeGroupService.readCodeGroupByGroupId(id))
    }
}