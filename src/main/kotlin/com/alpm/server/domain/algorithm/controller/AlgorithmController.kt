package com.alpm.server.domain.algorithm.controller

import com.alpm.server.domain.algorithm.dto.AlgorithmDto
import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.algorithm.service.AlgorithmService
import jakarta.annotation.PostConstruct
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// http://localhost:8090/mvc URL로 클라이언트가 요청을 하게되면 이 컨트롤러는 home.jsp 파일을 찾아 JSP파일이 변환된 HTML파일을 클라이언트에게 넘겨준다.

@RestController
@RequestMapping("/algorithm")
class AlgorithmController(
        val algorithmService: AlgorithmService
) {
    // create
    @PostMapping
    fun createAlgorithm(algorithmDto: AlgorithmDto): ResponseEntity<Any> {}

    // read - id
    @GetMapping("/{id}")
    fun readAlgorithm(@PathVariable("id") id: Long):ResponseEntity<Any> {
        return AlgorithmService.g
    }

    // read - all
    @GetMapping("")
    fun readAllAlgorithm():ResponseEntity<Any> {
        return ResponseEntity.ok().body(/*서비스 단에서 뭘 해줘야함 */)
    }

    // delete
    @DeleteMapping("/{id}")
    fun deleteAlgorithm(@PathVariable("id") id: Long):ResponseEntity<Any>{
        algorithmService.deleteAlgorithm(id)
        ResponseEntity.ok()
    }
}