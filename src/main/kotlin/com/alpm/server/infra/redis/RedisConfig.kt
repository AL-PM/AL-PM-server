package com.alpm.server.infra.redis

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableRedisRepositories
class RedisConfig(

    @Value("\${spring.data.redis.host}")
    private val host: String,

    @Value("\${spring.data.redis.port}")
    private val port: Int

) {

    @Bean
    fun redisConnectionFactory() : LettuceConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, String> {
        val redisTemplate = RedisTemplate<String, String>()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        redisTemplate.connectionFactory = redisConnectionFactory()

        return redisTemplate
    }

}