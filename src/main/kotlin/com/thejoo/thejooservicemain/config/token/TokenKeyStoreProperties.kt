package com.thejoo.thejooservicemain.config.token

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "token.keystore")
class TokenKeyStoreProperties {
    lateinit var type: String
    lateinit var filename: String
    lateinit var password: String
}
