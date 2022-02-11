package com.thejoo.thejooservicemain.infrastructure.filter

import org.springframework.web.filter.CommonsRequestLoggingFilter
import javax.servlet.http.HttpServletRequest

class CustomRequestLoggingFilter: CommonsRequestLoggingFilter() {
    init {
        isIncludeHeaders = true
        isIncludeQueryString = true
        isIncludePayload = true
        maxPayloadLength = 1000
        isIncludeHeaders = true
    }

    override fun beforeRequest(request: HttpServletRequest, message: String) {
        /**
         * Do nothing
         */
    }
}