package com.thejoo.thejooservicemain.config.token

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.time.Duration

abstract class TokenProperties {
    var alias: String? = null
    var password: String? = null
    var duration: Duration? = null
    var issuer: String? = null
    var audience: String? = null
}

@Component
@ConfigurationProperties(prefix = "token.properties.auth")
class AuthTokenProperties : TokenProperties()

@Component
@ConfigurationProperties(prefix = "token.properties.user-read")
class UserReadTokenProperties : TokenProperties()

@Component
@ConfigurationProperties(prefix = "token.properties.promotion")
class PromotionTokenProperties : TokenProperties()
