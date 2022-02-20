package com.thejoo.thejooservicemain.service

import com.auth0.jwt.JWT
import com.thejoo.thejooservicemain.config.AuthTokenConfig
import com.thejoo.thejooservicemain.config.TokenConfig
import com.thejoo.thejooservicemain.config.security.withClaim
import com.thejoo.thejooservicemain.config.token.TokenConstants
import com.thejoo.thejooservicemain.entity.Promotion
import com.thejoo.thejooservicemain.entity.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Date
import java.util.UUID

@Service
class JwtProviderService(
    private val authTokenConfig: AuthTokenConfig,
    private val userReadTokenConfig: TokenConfig,
    private val promotionTokenConfig: TokenConfig,
    private val adminAuthTokenConfig: AuthTokenConfig,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    fun generateAuthToken(user: User): String = generateAuthToken(user, authTokenConfig)

    fun generateAdminAuthToken(admin: User): String = generateAuthToken(admin, adminAuthTokenConfig)

    fun generateUserReadToken(user: User): String {
        val now = Instant.now()
        return JWT.create()
            .withJWTId(UUID.randomUUID().toString())
            .withIssuer(userReadTokenConfig.issuer)
            .withAudience(userReadTokenConfig.audience)
            .withSubject(user.id.toString())
            .withIssuedAt(Date.from(now))
            .withNotBefore(Date.from(now))
            .withExpiresAt(Date.from(now.plus(userReadTokenConfig.duration)))
            .sign(userReadTokenConfig.algorithm)
    }

    fun generatePromotionToken(owner: User, promotion: Promotion): String {
        val now = Instant.now()
        return JWT.create()
            .withJWTId(UUID.randomUUID().toString())
            .withIssuer(owner.id.toString())
            .withAudience(promotionTokenConfig.audience)
            .withSubject(promotion.id.toString())
            .withIssuedAt(Date.from(now))
            .withNotBefore(Date.from(now))
            .withExpiresAt(Date.from(now.plus(promotionTokenConfig.duration)))
            .withClaim(TokenConstants.CLAIM_NAME_STORE_ID, promotion.storeId)
            .withClaim(TokenConstants.CLAIM_NAME_PROMOTION_UUID, UUID.randomUUID().toString())
            .sign(promotionTokenConfig.algorithm)
    }

    private fun generateAuthToken(user: User, authTokenConfig: AuthTokenConfig): String =
        Instant.now()
            .let {
                JWT.create()
                    .withJWTId(UUID.randomUUID().toString())
                    .withSubject(user.username)
                    .withIssuer(authTokenConfig.issuer)
                    .withAudience(authTokenConfig.audience)
                    .withIssuedAt(Date.from(it))
                    .withNotBefore(Date.from(it))
                    .withExpiresAt(Date.from(it.plus(authTokenConfig.duration)))
                    .withClaim(TokenConstants.CLAIM_NAME_AUTH_TOKEN_TYPE, authTokenConfig.type)
                    .withClaim(TokenConstants.CLAIM_NAME_ROLES, user.roles)
                    .sign(authTokenConfig.algorithm)
            }
}
