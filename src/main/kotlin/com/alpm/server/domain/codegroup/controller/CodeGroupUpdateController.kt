package com.alpm.server.domain.codegroup.controller

import com.alpm.server.domain.codegroup.service.CodeGroupUpdateService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "CodeGroup Update", description = "CodeGroup 수정 API")
@RestController
@RequestMapping("/codeGroup")
class CodeGroupUpdateController (

    private val codeGroupUpdateService: CodeGroupUpdateService

) {

    @Operation(summary = "CodeGroup 가져오기")
    @PatchMapping("/import/{codeGroupId}")
    fun importCodeGroupById(@PathVariable("codeGroupId") codeGroupId: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok(codeGroupUpdateService.importCodeGroupToUser(codeGroupId))
    }

    @Operation(summary = "CodeGroup에 Algorithm 추가")
    @PatchMapping("/import/{codeGroupId}/{algorithmId}")
    fun importAlgorithmToCodeGroup(
        @PathVariable("codeGroupId") codeGroupId: Long,
        @PathVariable("algorithmId") algorithmId: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok().body(codeGroupUpdateService.importAlgorithmToCodeGroup(codeGroupId, algorithmId))
    }

}