package com.alpm.server.domain.algorithm.controller

import com.alpm.server.domain.algorithm.dto.AlgorithmCreateRequestDto
import com.alpm.server.domain.algorithm.dto.AlgorithmCreateResponseDto
import com.alpm.server.domain.algorithm.dto.AlgorithmDto
import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.algorithm.service.AlgorithmService
import com.alpm.server.domain.user.entity.User
import jakarta.annotation.PostConstruct
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.swing.Spring

// http://localhost:8090/mvc URL로 클라이언트가 요청을 하게되면 이 컨트롤러는 home.jsp 파일을 찾아 JSP파일이 변환된 HTML파일을 클라이언트에게 넘겨준다.

@RestController
@RequestMapping("/algorithm")
class AlgorithmController(
        val algorithmService: AlgorithmService
) {
    // create
    @PostMapping("/create")
    fun createAlgorithm(@RequestBody request : AlgorithmCreateRequestDto): ResponseEntity<HttpHeaders>  {
        val savedAlgorithm = algorithmService.saveAlgorithm(request)
        val response = AlgorithmCreateResponseDto(
                id = savedAlgorithm.id,
                owner = User(id = 1, name = "Admin"), // 사용자 정보
                name = savedAlgorithm.name,
                language = savedAlgorithm.language,
                referencedCount = 0, // 초기 값
                verified = false, // 초기 값
                content = savedAlgorithm.content,
                description = savedAlgorithm.description,
                createdAt = savedAlgorithm.createdAt,
                updatedAt = savedAlgorithm.updatedAt
        )
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    // read - id
    @GetMapping("/{id}")
    fun readAlgorithm(@PathVariable("id") id: Long):ResponseEntity<HttpHeaders>  {}

    // read - all
    @GetMapping("/")
    fun readAllAlgorithm():ResponseEntity<HttpHeaders>  {}

    // delete
    @DeleteMapping("/{id}")
    fun deleteAlgorithm(@PathVariable("id") id: Long):ResponseEntity<HttpHeaders> {
        algorithmService.deleteAlgorithm(id)
        return ResponseEntity.ok()
    }
}