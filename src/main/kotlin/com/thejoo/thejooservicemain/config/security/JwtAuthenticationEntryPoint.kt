package com.thejoo.thejooservicemain.config.security

import com.fasterxml.jackson.core.json.JsonWriteFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.thejoo.thejooservicemain.infrastructure.advice.TheJooException
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    private val log = LoggerFactory.getLogger(JwtAuthenticationEntryPoint::class.java)
    private val objectMapper = ObjectMapper()
        .also { it.factory.configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), true) }

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) = with(response!!) {
        val theJooException = TheJooException.ofUnauthorized()
        log.error("Authentication Failed", theJooException)
        contentType = MediaType.APPLICATION_JSON_VALUE
        status = theJooException.statusCodeValue
        writer.write(objectMapper.writeValueAsString(theJooException.toResponseBody()))
    }
}
