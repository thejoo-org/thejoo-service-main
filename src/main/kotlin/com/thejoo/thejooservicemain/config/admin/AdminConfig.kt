package com.thejoo.thejooservicemain.config.admin

import com.thejoo.thejooservicemain.entity.AdminUser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AdminConfig {
    @Bean
    fun adminUser(adminUserProperties: AdminUserProperties) =
        AdminUser(
            name = adminUserProperties.name,
            email = adminUserProperties.email,
            password = adminUserProperties.password,
        )
}
