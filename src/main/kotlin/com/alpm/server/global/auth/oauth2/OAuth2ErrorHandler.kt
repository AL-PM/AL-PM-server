package com.alpm.server.global.auth.oauth2

import com.alpm.server.global.exception.CustomException
import com.alpm.server.global.exception.ErrorCode
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.DefaultResponseErrorHandler

@Component
class OAuth2ErrorHandler: DefaultResponseErrorHandler() {

    override fun handleError(response: ClientHttpResponse) {
        throw CustomException(ErrorCode.OAUTH2_FAILURE)
    }

}