package com.alpm.server.domain.codegroup.controller


import com.alpm.server.domain.codegroup.dto.*
import com.alpm.server.domain.codegroup.service.CodeGroupService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/codegroup")
class CodeGroupController(

    private val codeGroupService: CodeGroupService

) {
    @Operation(summary = "Code Group 생성")
    @PostMapping("/create")
    fun createCodeGroup(@RequestBody request: CodeGroupCreateRequestDto): ResponseEntity<CodeGroupDto> {
        return ResponseEntity.ok().body(codeGroupService.saveCodeGroup(request))
    }

//    @Operation(summary = "CodeGroup 가져오기")
//    @PutMapping("/import/{id}")
//    fun putCodeGroup(@PathVariable("id") id: Long): ResponseEntity<Unit> {
//        return ResponseEntity.ok(codeGroupService.putCodeGroupByID(id))
//    }

    @Operation(summary = "CodeGroup 전체 조회")
    @GetMapping("")
    fun readAllCodeGroups(): ResponseEntity<List<CodeGroupListRequestDto>> {
        return ResponseEntity.ok().body(codeGroupService.readAllCodeGroups())
    }

    @Operation(summary = "특정 User의 CodeGroup 전체 조회")
    @GetMapping("/user/{id}")
    fun readAllCodeGroupsByUserID(@PathVariable("id") id : Long): ResponseEntity<List<CodeGroupRequestByUserIdDto>> {
        return ResponseEntity.ok().body(codeGroupService.readAllCodeGroupsByUserID(id))
    }

    @Operation(summary = "CodeGroup 단일 조회")
    @GetMapping("/{id}")
    fun readAllCodeInCodeGroupByGroupID(@PathVariable("id") id : Long): ResponseEntity<Any> {
        return ResponseEntity.ok().body(codeGroupService.readAllCodeInCodeGroupByGroupID(id))
    }
}