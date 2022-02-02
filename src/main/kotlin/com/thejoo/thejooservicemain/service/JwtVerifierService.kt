package com.thejoo.thejooservicemain.service

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.thejoo.thejooservicemain.config.TokenConfig
import com.thejoo.thejooservicemain.config.token.TokenConstants
import com.thejoo.thejooservicemain.entity.Role
import com.thejoo.thejooservicemain.entity.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class JwtVerifierService(
    private val authTokenConfig: TokenConfig,
    private val userReadTokenConfig: TokenConfig,
    private val promotionTokenConfig: TokenConfig,
) {
    val log: Logger = LoggerFactory.getLogger(javaClass)

    fun verifyAndBuildUserFromAuthToken(token: String): User {
        val decodedJWT = JWT.require(authTokenConfig.algorithm)
            .withIssuer(authTokenConfig.issuer)
            .withAudience(authTokenConfig.audience)
            .build()
            .verify(token)
        val userId = decodedJWT.subject.toLong()
        val roles = decodedJWT.getClaim(TokenConstants.CLAIM_NAME_ROLES).asList(Role::class.java)
        return User(id = userId, roles = roles)
    }

    fun verifyUserReadToken(token: String): DecodedJWT =
        JWT.require(userReadTokenConfig.algorithm)
            .withIssuer(userReadTokenConfig.issuer)
            .withAudience(userReadTokenConfig.audience)
            .build()
            .verify(token)

    fun verifyPromotionToken(token: String, ownerId: Long): DecodedJWT =
        JWT.require(promotionTokenConfig.algorithm)
            .withIssuer(ownerId.toString())
            .withAudience(promotionTokenConfig.audience)
            .build()
            .verify(token)
}