package com.thejoo.thejooservicemain.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.thejoo.thejooservicemain.infrastructure.advice.TheJooException
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAccessDeniedHandler: AccessDeniedHandler {
    private val objectMapper = ObjectMapper()

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) = with(response!!) {
        val theJooException = TheJooException.ofForbidden()
        contentType = MediaType.APPLICATION_JSON_VALUE
        status = theJooException.statusCodeValue
        writer.write(objectMapper.writeValueAsString(theJooException.toResponseBody()))
    }
}