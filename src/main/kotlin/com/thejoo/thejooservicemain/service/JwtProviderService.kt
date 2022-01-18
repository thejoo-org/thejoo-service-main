package com.thejoo.thejooservicemain.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.thejoo.thejooservicemain.config.token.TokenKeystoreProperties
import com.thejoo.thejooservicemain.config.token.TokenSigningKeyProperties
import com.thejoo.thejooservicemain.entity.User
import com.thejoo.thejooservicemain.repository.UserRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.security.KeyStore
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey
import java.time.Duration
import java.time.Instant
import java.util.*
import kotlin.RuntimeException

@Service
class JwtProviderService(
    private val userRepository: UserRepository,
    tokenKeystoreProperties: TokenKeystoreProperties,
    tokenSigningKeyProperties: TokenSigningKeyProperties,
) {
    private val signingAlg: Algorithm

    init {
        val keyStore = KeyStore.getInstance(tokenKeystoreProperties.type)
        val keyStoreInputStream = ClassLoader.getSystemResourceAsStream(tokenKeystoreProperties.filename)
        keyStore.load(keyStoreInputStream, tokenKeystoreProperties.password.toCharArray())
        val signingPk = keyStore.getCertificate(tokenSigningKeyProperties.alias).publicKey as ECPublicKey
        val signingSk = keyStore.getKey(tokenSigningKeyProperties.alias, tokenSigningKeyProperties.password.toCharArray()) as ECPrivateKey
        signingAlg = Algorithm.ECDSA256(signingPk, signingSk)
    }

    fun generateAuthToken(userId: Long): String = userRepository.findById(userId)
        .map(this::generateJwt)
        .orElseThrow(::RuntimeException)

    fun generateReadOnlyQrToken(userId: Long): String = userRepository.findById(userId)
        .map(this::generateJwt)
        .orElseThrow(::RuntimeException)

    private fun generateJwt(user: User): String {
        val now = Instant.now()
        return JWT.create()
            .withJWTId(UUID.randomUUID().toString())
            .withIssuer("thejoo-token-service")
            .withAudience("thejoo-services")
            .withSubject(user.id.toString())
            .withIssuedAt(Date.from(now))
            .withNotBefore(Date.from(now))
            .withExpiresAt(Date.from(now.plus(Duration.ofHours(12))))
            .sign(signingAlg)
    }

    fun deriveAuthentication(jwt: String?): Authentication {
        val user = JWT.require(signingAlg)
            .withIssuer("thejoo-token-service")
            .withAudience("thejoo-services")
            .build()
            .verify(jwt)
            .subject
            .toLong()
            .let { User(id = it) }

        return UsernamePasswordAuthenticationToken(user, jwt, user.authorities)
    }
}