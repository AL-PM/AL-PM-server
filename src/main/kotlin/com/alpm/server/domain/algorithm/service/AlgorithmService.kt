package com.alpm.server.domain.algorithm.service

import com.alpm.server.domain.algorithm.dao.AlgorithmRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

// 서비스 객체는 스프링 MVC에서는 서비스 인터페이스를 구현한 객체이다.
// 이런 방식으로 객체를 구현하는 이유는 단순히 if else 문의 단순 중첩문의 유지보수가 어려운 코드를 양산하는 것이 아닌 객체지향적인 방법을 활용하여 유지보수가 편한 코드를 작성하도록 유도

@Service
class AlgorithmService(
        private val algorithmRepository: AlgorithmRepository
){
    fun deleteAlgorithm(id: Long){
        algorithmRepository.deleteById(id)
    }
}
