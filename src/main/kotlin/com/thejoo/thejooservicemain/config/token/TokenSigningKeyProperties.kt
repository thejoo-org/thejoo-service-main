package com.thejoo.thejooservicemain.config.token

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "token.signing-key")
data class TokenSigningKeyProperties(
    val alias: String,
    val password: String,
)