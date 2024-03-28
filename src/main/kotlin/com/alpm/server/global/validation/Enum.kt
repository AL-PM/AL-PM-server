package com.alpm.server.global.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.Enum
import kotlin.reflect.KClass

@MustBeDocumented
@Target(allowedTargets = [
    AnnotationTarget.FIELD
])
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EnumValidator::class])
annotation class Enum (

    val message: String = "Enum에 없는 값입니다",

    val groups: Array<KClass<*>> = [],

    val payload: Array<KClass<out Payload>> = [],

    val enumClass: KClass<out Enum<*>>,

    val ignoreCase: Boolean = false

)