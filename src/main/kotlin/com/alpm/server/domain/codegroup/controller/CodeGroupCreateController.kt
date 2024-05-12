package com.alpm.server.domain.codegroup.controller

import com.alpm.server.domain.codegroup.dto.request.CodeGroupCreateRequestDto
import com.alpm.server.domain.codegroup.dto.response.CodeGroupDetailResponseDto
import com.alpm.server.domain.codegroup.service.CodeGroupCreateService
import com.alpm.server.global.validation.ValidationSequence
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "CodeGroup Create", description = "CodeGroup 생성 API")
@RestController
@RequestMapping("/codeGroup")
class CodeGroupCreateController (

    private val codeGroupCreateService: CodeGroupCreateService

) {

    @Operation(summary = "CodeGroup 생성")
    @PostMapping("/create")
    fun createCodeGroup(
        @RequestBody @Validated(value = [ValidationSequence::class]) request: CodeGroupCreateRequestDto
    ): ResponseEntity<CodeGroupDetailResponseDto> {
        return ResponseEntity.ok().body(codeGroupCreateService.createCodeGroup(request))
    }

}