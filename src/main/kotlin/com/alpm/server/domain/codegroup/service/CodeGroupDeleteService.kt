package com.alpm.server.domain.codegroup.service

import com.alpm.server.domain.codegroup.dao.CodeGroupRepository
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class CodeGroupDeleteService (

    private val codeGroupRepository: CodeGroupRepository

) {

    fun deleteById(id: Long) {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        val codeGroup = codeGroupRepository.findByIdOrNull(id) ?: throw CustomException(ErrorCode.CODE_GROUP_NOT_FOUND)

        // 다른 사용자의 CodeGroup을 삭제하려 하는 경우
        if (user.id!! != codeGroup.owner.id) {
            throw CustomException(ErrorCode.NO_GRANT)
        }

        codeGroupRepository.deleteById(id)
    }

}