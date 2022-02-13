package com.thejoo.thejooservicemain.config

import com.thejoo.thejooservicemain.config.security.JwtAccessDeniedHandler
import com.thejoo.thejooservicemain.config.security.JwtAuthenticationEntryPoint
import com.thejoo.thejooservicemain.config.security.JwtSecurityConfig
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
class SecurityConfig(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    private val jwtSecurityConfig: JwtSecurityConfig,
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http!!
            .csrf().disable()
            .cors().disable()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/doc").permitAll()
            .antMatchers("/swagger-ui/**").permitAll()
            .antMatchers("/v3/api-docs/**").permitAll()
            .antMatchers("/api/test/tokens/**").permitAll()
            .antMatchers("/api/admin/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .apply(jwtSecurityConfig)
    }
}
