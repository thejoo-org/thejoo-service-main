package com.thejoo.thejooservicemain.config.security

import com.fasterxml.jackson.core.json.JsonWriteFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.thejoo.thejooservicemain.infrastructure.advice.TheJooException
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAccessDeniedHandler: AccessDeniedHandler {
    private val log = LoggerFactory.getLogger(JwtAccessDeniedHandler::class.java)
    private val objectMapper = ObjectMapper()
        .also { it.factory.configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), true) }

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) = with(response!!) {
        val theJooException = TheJooException.ofForbidden()
        log.error("Access Denied", theJooException)
        contentType = MediaType.APPLICATION_JSON_VALUE
        status = theJooException.statusCodeValue
        writer.write(objectMapper.writeValueAsString(theJooException.toResponseBody()))
    }
}