package com.thejoo.thejooservicemain.config.token

import org.springframework.boot.context.properties.ConfigurationProperties


abstract class TokenKeyProperties {
    lateinit var alias: String
    lateinit var password: String
}


@ConfigurationProperties(prefix = "token.signing-key.auth")
class AuthTokenKeyProperties: TokenKeyProperties()

@ConfigurationProperties(prefix = "token.signing-key.promotion-read")
class PromotionReadKeyProperties: TokenKeyProperties()
