package com.alpm.server.global.config.dateformat

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.format.DateTimeFormatter

@Configuration
class DateFormatConfig {

    private val dateFormat = "yyyy-MM-dd"

    private val dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"

    @Bean
    fun jackson2ObjectMapperBuilder(): Jackson2ObjectMapperBuilder {
        return Jackson2ObjectMapperBuilder().simpleDateFormat(dateTimeFormat)
            .serializers(LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)))
            .serializers(LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
    }

}