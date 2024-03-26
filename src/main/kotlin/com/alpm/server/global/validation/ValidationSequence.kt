package com.alpm.server.global.validation

import jakarta.validation.GroupSequence

@GroupSequence(value = [
    ValidationGroup.NotNullGroup::class,
    ValidationGroup.SizeGroup::class,
    ValidationGroup.MinMaxGroup::class
])
interface ValidationSequence {
}