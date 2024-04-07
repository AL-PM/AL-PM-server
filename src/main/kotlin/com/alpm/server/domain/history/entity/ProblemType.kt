package com.alpm.server.domain.history.entity

enum class ProblemType (

    val type: String

) {

    TRACE("Trace"),
    FILL("Fill"),
    BLOCK("Block"),
    SEQUENCE("Sequence")
    ;

}