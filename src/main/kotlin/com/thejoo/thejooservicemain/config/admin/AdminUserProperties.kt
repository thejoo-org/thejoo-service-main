package com.thejoo.thejooservicemain.config.admin

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "admin.user")
class AdminUserProperties {
    lateinit var id: String
    lateinit var password: String
    lateinit var name: String
    lateinit var email: String
}
