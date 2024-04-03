package com.alpm.server.domain.codegroup.controller


import com.alpm.server.domain.algorithm.dto.AlgorithmDto
import com.alpm.server.domain.codegroup.dto.CodegroupCreateRequestDto
import com.alpm.server.domain.codegroup.dto.CodegroupDto
import com.alpm.server.domain.codegroup.dto.CodegroupListRequestDto
import com.alpm.server.domain.codegroup.service.CodegroupService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/codegroup")
class CodegroupController(

    private val codegroupService: CodegroupService

) {
    @Operation(summary = "Code Group 생성")
    @PostMapping("/create")
    fun createCodegroup(@RequestBody request: CodegroupCreateRequestDto): ResponseEntity<CodegroupDto> {
        return ResponseEntity.ok().body(codegroupService.saveCodegroup(request))
    }

    @Operation(summary = "CodeGroup 가져오기")
    @PutMapping("/import/{id}")
    fun putCodegroup(@PathVariable("id") id: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok(codegroupService.putCodegroupByID(id))
    }

    @Operation(summary = "CodeGroup 전체 조회")
    @GetMapping("")
    fun readAllCodegroups(): ResponseEntity<List<CodegroupListRequestDto>> {
        return ResponseEntity.ok().body(codegroupService.readAllCodegroups())
    }



}