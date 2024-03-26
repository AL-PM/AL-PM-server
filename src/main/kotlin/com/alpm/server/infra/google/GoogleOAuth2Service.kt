package com.alpm.server.infra.google

import com.alpm.server.global.auth.oauth2.OAuth2ErrorHandler
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class GoogleOAuth2Service(

    @Value("\${oauth2.google.client-id}")
    private val clientId: String,

    @Value("\${oauth2.google.client-secret}")
    private val clientSecret: String,

    @Value("\${oauth2.google.authentication-uri}")
    private val authenticationUri: String,

    @Value("\${oauth2.google.redirect-uri}")
    private val redirectUri: String,

    @Value("\${oauth2.google.token-uri}")
    private val tokenUri: String,

    @Value("\${oauth2.google.resource-uri}")
    private val resourceUri: String,

    private val oAuth2ErrorHandler: OAuth2ErrorHandler

) {

    fun oAuth2Redirect(): HttpHeaders {
        val params = LinkedMultiValueMap<String, String>()
        params["scope"] = "https://www.googleapis.com/auth/userinfo.profile"
        params["access_type"] = "offline"
        params["include_granted_scopes"] = "true"
        params["response_type"] = "code"
        params["state"] = "state_parameter_passthrough_value"
        params["redirect_uri"] = redirectUri
        params["client_id"] = clientId

        val uriBuilder = UriComponentsBuilder.fromHttpUrl(authenticationUri)
            .queryParams(params)

        val headers = HttpHeaders()
        headers.location = uriBuilder.build().toUri()

        return headers
    }

    fun getAccessToken(code: String, registrationId: String): String {
        val params = LinkedMultiValueMap<String, String>()
        params.add("code", code)
        params.add("client_id", clientId)
        params.add("client_secret", clientSecret)
        params.add("redirect_uri", redirectUri)
        params.add("grant_type", "authorization_code")

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val entity = HttpEntity(params, headers)

        val restTemplate = RestTemplate()
        restTemplate.errorHandler = oAuth2ErrorHandler
        val response = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode::class.java)
        val accessTokenBody = response.body

        return accessTokenBody!!.get("access_token").asText()!!
    }

    fun getUserResource(accessToken: String, registrationId: String): JsonNode? {
        val restTemplate = RestTemplate()
        restTemplate.errorHandler = oAuth2ErrorHandler

        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer $accessToken")

        val entity = HttpEntity(null, headers)
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode::class.java).body
    }

}