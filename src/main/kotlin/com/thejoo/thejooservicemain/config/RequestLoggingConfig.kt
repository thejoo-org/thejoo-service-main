package com.thejoo.thejooservicemain.config

import com.thejoo.thejooservicemain.infrastructure.filter.CustomRequestLoggingFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter

@Configuration
class RequestLoggingConfig {
    @Bean
    fun logFilter(): CommonsRequestLoggingFilter = CustomRequestLoggingFilter()
}
