package com.thejoo.thejooservicemain.config.token

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "token.keystore")
data class TokenKeystoreProperties(
    val type: String,
    val filename: String,
    val password: String,
)