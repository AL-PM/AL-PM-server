package com.alpm.server.global.common.model

enum class ProblemType (

    val type: String

) {

    TRACE("Trace"),
    FILL("Fill"),
    BLOCK("Block"),
    SEQUENCE("Sequence")
    ;

}