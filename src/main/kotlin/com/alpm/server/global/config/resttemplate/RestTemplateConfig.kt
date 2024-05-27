package com.alpm.server.global.config.resttemplate

import com.fasterxml.jackson.databind.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate


@Configuration
class RestTemplateConfig (

    private val objectMapper: ObjectMapper

) {

    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        val messageConverters: MutableList<HttpMessageConverter<*>> = ArrayList()
        val jsonMessageConverter = MappingJackson2HttpMessageConverter()
        jsonMessageConverter.objectMapper = objectMapper
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        messageConverters.add(jsonMessageConverter)
        restTemplate.messageConverters = messageConverters

        return restTemplate
    }

}