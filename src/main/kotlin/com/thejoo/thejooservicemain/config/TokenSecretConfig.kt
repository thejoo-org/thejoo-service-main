package com.thejoo.thejooservicemain.config

import com.auth0.jwt.algorithms.Algorithm
import com.thejoo.thejooservicemain.config.token.AuthTokenKeyProperties
import com.thejoo.thejooservicemain.config.token.PromotionReadKeyProperties
import com.thejoo.thejooservicemain.config.token.TokenKeyProperties
import com.thejoo.thejooservicemain.config.token.TokenKeystoreProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.KeyStore
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

@Configuration
class TokenSecretConfig(
    tokenKeystoreProperties: TokenKeystoreProperties,
    private val authTokenKeyProperties: AuthTokenKeyProperties,
    private val promotionReadKeyProperties: PromotionReadKeyProperties,
) {
    private val keyStore: KeyStore = KeyStore.getInstance(tokenKeystoreProperties.type)

    init {
        val keyStoreInputStream = ClassLoader.getSystemResourceAsStream(tokenKeystoreProperties.filename)
        keyStore.load(keyStoreInputStream, tokenKeystoreProperties.password.toCharArray())
    }

    @Bean("tokenKeyStore")
    fun tokenKeyStore(): KeyStore {
        return keyStore
    }

    @Bean("authTokenAlgorithm")
    fun authTokenAlgorithm(): Algorithm = generateECDSA256Algorithm(authTokenKeyProperties)

    @Bean("promotionReadTokenAlgorithm")
    fun promotionReadTokenAlgorithm(): Algorithm = generateECDSA256Algorithm(promotionReadKeyProperties)

    private fun generateECDSA256Algorithm(tokenKeyProperties: TokenKeyProperties) =
        Algorithm.ECDSA256(
            keyStore.getCertificate(tokenKeyProperties.alias).publicKey as ECPublicKey,
            keyStore.getKey(tokenKeyProperties.alias, tokenKeyProperties.password.toCharArray()) as ECPrivateKey,
        )
}