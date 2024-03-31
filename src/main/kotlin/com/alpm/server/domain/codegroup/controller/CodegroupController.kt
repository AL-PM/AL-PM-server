package com.alpm.server.domain.codegroup.controller


import com.alpm.server.domain.codegroup.dto.CodegroupCreateRequestDto
import com.alpm.server.domain.codegroup.dto.CodegroupDto
import com.alpm.server.domain.codegroup.service.CodegroupService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}