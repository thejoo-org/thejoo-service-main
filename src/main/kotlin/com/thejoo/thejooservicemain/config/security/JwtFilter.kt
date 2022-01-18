package com.thejoo.thejooservicemain.config.security

import com.thejoo.thejooservicemain.service.JwtProviderService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.Exception
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(
    private val jwtProviderService: JwtProviderService,
): OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = getJwt(request)
        log.info(jwt)
        try {
            SecurityContextHolder.getContext().authentication = jwtProviderService.deriveAuthentication(jwt)
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            filterChain.doFilter(request, response)
        }
    }

    private fun getJwt(request: HttpServletRequest) =
        request.getHeader(HttpHeaders.AUTHORIZATION)?.replaceFirst("Bearer ", "")
}