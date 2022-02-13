package com.thejoo.thejooservicemain.service

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.thejoo.thejooservicemain.config.AuthTokenConfig
import com.thejoo.thejooservicemain.config.AuthTokenType
import com.thejoo.thejooservicemain.config.TokenConfig
import com.thejoo.thejooservicemain.config.security.withClaim
import com.thejoo.thejooservicemain.config.token.TokenConstants
import com.thejoo.thejooservicemain.entity.AdminUser
import com.thejoo.thejooservicemain.entity.Role
import com.thejoo.thejooservicemain.entity.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class JwtVerifierService(
    private val authTokenConfig: AuthTokenConfig,
    private val adminAuthTokenConfig: AuthTokenConfig,
    private val userReadTokenConfig: TokenConfig,
    private val promotionTokenConfig: TokenConfig,
    private val adminUser: AdminUser,
) {
    val log: Logger = LoggerFactory.getLogger(javaClass)

    fun decodeAuthTypeFromAuthToken(token: String) =
        JWT.decode(token).getClaim(TokenConstants.CLAIM_NAME_AUTH_TOKEN_TYPE).asString().let(AuthTokenType::valueOf)

    fun verifyAndBuildUserFromAuthToken(token: String): User {
        val decodedJWT = verifyAuthToken(token, authTokenConfig)
        val userId = decodedJWT.subject.toLong()
        val roles = decodedJWT.getClaim(TokenConstants.CLAIM_NAME_ROLES).asList(Role::class.java)
        return User(id = userId, roles = roles)
    }

    fun verifyAndBuildAdminUserFromAuthToken(token: String): AdminUser =
        verifyAuthToken(token, adminAuthTokenConfig).let { adminUser }

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

    private fun verifyAuthToken(token: String, authTokenConfig: AuthTokenConfig) =
        JWT.require(authTokenConfig.algorithm)
            .withIssuer(authTokenConfig.issuer)
            .withAudience(authTokenConfig.audience)
            .withClaim(TokenConstants.CLAIM_NAME_AUTH_TOKEN_TYPE, authTokenConfig.type)
            .build()
            .verify(token)
}
