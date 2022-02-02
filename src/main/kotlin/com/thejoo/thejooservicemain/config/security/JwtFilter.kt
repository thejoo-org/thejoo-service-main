package com.thejoo.thejooservicemain.config.security

import com.thejoo.thejooservicemain.service.JwtVerifierService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(
    private val jwtVerifierService: JwtVerifierService,
): OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            val jwt = request.getJwt()
            log.info(jwt)
            SecurityContextHolder.getContext().authentication = jwtVerifierService.verifyAndBuildUserFromAuthToken(token = jwt)
                .let { UsernamePasswordAuthenticationToken(it, jwt, it.authorities) }
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            log.error("Auth token NOT configured...")
            filterChain.doFilter(request, response)
        }
    }

    private fun HttpServletRequest.getJwt(): String =
        this.getHeader(HttpHeaders.AUTHORIZATION)!!.replaceFirst("Bearer ", "")
}