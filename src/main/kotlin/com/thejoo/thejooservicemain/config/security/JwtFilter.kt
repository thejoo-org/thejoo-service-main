package com.thejoo.thejooservicemain.config.security

import com.thejoo.thejooservicemain.config.AuthTokenType
import com.thejoo.thejooservicemain.service.JwtVerifierService
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
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            val jwt = request.getJwt()
            val authTokenType = jwtVerifierService.decodeAuthTypeFromAuthToken(token = jwt)
            SecurityContextHolder.getContext().authentication =
                deriveJwtVerifierFunction(authTokenType).call(jwt)
                    .let { UsernamePasswordAuthenticationToken(it, jwt, it.authorities) }
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            filterChain.doFilter(request, response)
        }
    }

    private fun deriveJwtVerifierFunction(authTokenType: AuthTokenType) =
        when (authTokenType) {
            AuthTokenType.COMMON -> jwtVerifierService::verifyAndBuildUserFromAuthToken
            AuthTokenType.ADMIN -> jwtVerifierService::verifyAndBuildAdminUserFromAuthToken
        }

    private fun HttpServletRequest.getJwt(): String = getHeader(HttpHeaders.AUTHORIZATION)!!.replaceFirst("Bearer ", "")
}
