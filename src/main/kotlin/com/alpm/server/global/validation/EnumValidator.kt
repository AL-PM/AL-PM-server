package com.alpm.server.global.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext


class EnumValidator : ConstraintValidator<Enum?, String> {

    private var annotation: Enum? = null

    override fun initialize(constraintAnnotation: Enum?) {
        this.annotation = constraintAnnotation
    }

    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
        val enumValues: Array<out kotlin.Enum<*>> = annotation!!.enumClass.java.enumConstants

        for (enumValue in enumValues) {
            if (value == enumValue.toString()) {
                return true
            }
        }

        return false
    }

}