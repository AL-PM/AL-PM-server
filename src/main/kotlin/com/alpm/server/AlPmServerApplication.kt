package com.alpm.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AlPmServerApplication

fun main(args: Array<String>) {
    runApplication<AlPmServerApplication>(*args)
}
