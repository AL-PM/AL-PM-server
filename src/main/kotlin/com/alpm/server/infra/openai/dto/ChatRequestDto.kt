package com.alpm.server.infra.openai.dto

data class ChatRequestDto (

    val model: String,

    val messages: List<ChatMessageDto>,

    val temperature: Double,

    val maxTokens: Int

) {

}