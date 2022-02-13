package com.thejoo.thejooservicemain.config

import com.auth0.jwt.algorithms.Algorithm
import com.thejoo.thejooservicemain.config.token.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.security.KeyStore
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey
import java.time.Duration

@Configuration
class JwtConfig(
    tokenKeyStoreProperties: TokenKeyStoreProperties,
) {
    private val keyStore: KeyStore = KeyStore.getInstance(tokenKeyStoreProperties.type)
    init {
        val keyStoreInputStream = ClassPathResource(tokenKeyStoreProperties.filename).inputStream
        keyStore.load(keyStoreInputStream, tokenKeyStoreProperties.password.toCharArray())
    }

    @Bean("authTokenConfig")
    fun authTokenConfig(authTokenProperties: AuthTokenProperties) =
        AuthTokenConfig(AuthTokenType.COMMON, authTokenProperties, keyStore)

    @Bean("userReadTokenConfig")
    fun userReadTokenConfig(userReadTokenProperties: UserReadTokenProperties) =
        TokenConfig(userReadTokenProperties, keyStore)

    @Bean("promotionTokenConfig")
    fun promotionTokenConfig(promotionTokenProperties: PromotionTokenProperties) =
        TokenConfig(promotionTokenProperties, keyStore)

    @Bean("adminAuthTokenConfig")
    fun adminAuthTokenConfig(adminAuthTokenProperties: AuthTokenProperties) =
        AuthTokenConfig(AuthTokenType.ADMIN, adminAuthTokenProperties, keyStore)
}

open class TokenConfig(
    tokenProperties: TokenProperties,
    keyStore: KeyStore
) {
    var issuer: String?
    var audience: String?
    var duration: Duration?
    var algorithm: Algorithm? = null

    init {
        duration = tokenProperties.duration
        issuer = tokenProperties.issuer
        audience = tokenProperties.audience
        val alias = tokenProperties.alias
        val password = tokenProperties.password
        if (alias != null && password != null) {
            val publicKey = keyStore.getCertificate(alias).publicKey as ECPublicKey
            val privateKey = keyStore.getKey(alias, password.toCharArray()) as ECPrivateKey
            algorithm = Algorithm.ECDSA256(publicKey, privateKey)
        }
    }
}

class AuthTokenConfig(
    var type: AuthTokenType,
    tokenProperties: TokenProperties,
    keyStore: KeyStore,
) : TokenConfig(tokenProperties, keyStore) {
    fun isForAdmin() = type == AuthTokenType.COMMON
}

enum class AuthTokenType {
    COMMON, ADMIN
}
