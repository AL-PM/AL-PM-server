package com.alpm.server.domain.codegroup.dto

import com.alpm.server.domain.algorithm.entity.Algorithm
import com.alpm.server.domain.user.dto.OwnerDto
import com.alpm.server.domain.user.entity.User
import com.alpm.server.global.common.model.Language
import jakarta.persistence.*
import java.time.LocalDateTime

class CodegroupDto(

    val id: Long,

    val name: String,

    val referencedCount: Int,

    val verified: Boolean, // Official-User 구분

    val visible: Boolean,

    val language: Language, //(enum - C/C++, JAVA, Python),

    val owner: OwnerDto,

    val groupsAlgorithms: List<Algorithm>,

    val myCodeGroups: List<User>
) {
}