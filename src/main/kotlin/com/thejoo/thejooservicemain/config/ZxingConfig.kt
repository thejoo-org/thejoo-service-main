package com.thejoo.thejooservicemain.config

import com.google.zxing.MultiFormatWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ZxingConfig {
    @Bean
    fun multiFormatWriter(): MultiFormatWriter = MultiFormatWriter()
}