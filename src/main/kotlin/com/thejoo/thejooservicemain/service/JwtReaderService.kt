package com.thejoo.thejooservicemain.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.thejoo.thejooservicemain.entity.Role
import com.thejoo.thejooservicemain.entity.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class JwtReaderService(
    private val authTokenAlgorithm: Algorithm,
    private val promotionReadTokenAlgorithm: Algorithm,
) {
    val log: Logger = LoggerFactory.getLogger(javaClass)

    fun verifyUserFromAuthToken(token: String): User {
        val decodedJWT = JWT.require(authTokenAlgorithm)
            .withIssuer("thejoo-token-service")
            .withAudience("thejoo-services")
            .build()
            .verify(token)
        val userId = decodedJWT.subject.toLong()
        val roles = decodedJWT.getClaim("roles").asList(Role::class.java)
        return User(id = userId, roles = roles)
    }

    fun verifyUserIdFromPromotionReadToken(token: String): Long =
        JWT.require(promotionReadTokenAlgorithm)
            .withIssuer("thejoo-token-service")
            .withAudience("thejoo-services")
            .build()
            .verify(token)
            .subject
            .toLong()
}