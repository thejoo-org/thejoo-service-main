package com.thejoo.thejooservicemain.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.thejoo.thejooservicemain.entity.User
import com.thejoo.thejooservicemain.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
class JwtProviderService(
    private val authTokenAlgorithm: Algorithm,
    private val promotionReadTokenAlgorithm: Algorithm,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    fun generateAuthToken(user: User): String {
        val now = Instant.now()
        return JWT.create()
            .withJWTId(UUID.randomUUID().toString())
            .withIssuer("thejoo-token-service")
            .withAudience("thejoo-services")
            .withSubject(user.id.toString())
            .withIssuedAt(Date.from(now))
            .withNotBefore(Date.from(now))
            .withExpiresAt(Date.from(now.plus(Duration.ofHours(12))))
            .withClaim(ROLES_CLAIM_NAME, user.roles)
            .sign(authTokenAlgorithm)
    }

    fun generatePromotionReadToken(user: User): String {
        val now = Instant.now()
        return JWT.create()
            .withJWTId(UUID.randomUUID().toString())
            .withIssuer("thejoo-token-service")
            .withAudience("thejoo-services")
            .withSubject(user.id.toString())
            .withIssuedAt(Date.from(now))
            .withNotBefore(Date.from(now))
            .withExpiresAt(Date.from(now.plus(Duration.ofHours(12))))
            .withClaim(ROLES_CLAIM_NAME, user.roles)
            .sign(promotionReadTokenAlgorithm)
    }

    companion object {
        const val ROLES_CLAIM_NAME = "roles"
    }
}