package com.thejoo.thejooservicemain.config

import com.thejoo.thejooservicemain.infrastructure.advice.PrincipalUserArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val principalUserArgumentResolver: PrincipalUserArgumentResolver,
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(principalUserArgumentResolver)
        super.addArgumentResolvers(resolvers)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
        super.addCorsMappings(registry)
    }
}
