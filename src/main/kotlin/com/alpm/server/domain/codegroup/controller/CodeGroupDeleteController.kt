package com.alpm.server.domain.codegroup.controller

import com.alpm.server.domain.codegroup.service.CodeGroupDeleteService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "CodeGroup Update", description = "CodeGroup 삭제 API")
@RestController
@RequestMapping("/codeGroup")
class CodeGroupDeleteController (

    private val codeGroupDeleteService: CodeGroupDeleteService

) {

    @Operation(summary = "CodeGroup 삭제")
    @DeleteMapping("/{id}")
    fun deleteCodeGroupById(@PathVariable id: Long){
        codeGroupDeleteService.deleteById(id)
    }

}