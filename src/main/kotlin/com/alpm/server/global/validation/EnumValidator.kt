package com.alpm.server.global.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext


class EnumValidator : ConstraintValidator<Enum?, String?> {

    private var annotation: Enum? = null

    override fun initialize(constraintAnnotation: Enum?) {
        this.annotation = constraintAnnotation
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        // null 값이 들어왔을 때의 판단은 enum 어노테이션이 아닌 NotNull, NotEmpty, NotBlank 에서 판단
        if (value == null) return true

        val enumValues: Array<out kotlin.Enum<*>> = annotation!!.enumClass.java.enumConstants

        for (enumValue in enumValues) {
            if (value == enumValue.toString()) {
                return true
            }
        }

        return false
    }

}