package com.alpm.server.global.config.security

import com.alpm.server.global.auth.jwt.JwtAuthenticationEntryPoint
import com.alpm.server.global.auth.jwt.JwtAuthenticationFilter
import com.alpm.server.global.auth.jwt.JwtAuthenticationService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig (

    private val jwtAuthenticationService: JwtAuthenticationService,

    private val objectMapper: ObjectMapper

) {

    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf {
            it.disable()
        }
        .sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        .authorizeHttpRequests {
            it.requestMatchers("/oauth2/authorization/**", "/oauth2/code/**", "/algorithm/anonymous/**",
                "/swagger-ui/**", "/v3/api-docs/**")
                .permitAll()
                .anyRequest().authenticated()
        }
        .addFilterBefore(JwtAuthenticationFilter(jwtAuthenticationService), UsernamePasswordAuthenticationFilter::class.java)
        .exceptionHandling {
            it.authenticationEntryPoint(JwtAuthenticationEntryPoint(objectMapper))
        }
        .build()!!

}