package com.thejoo.thejooservicemain.controller

import com.thejoo.thejooservicemain.controller.domain.GetTokenResponse
import com.thejoo.thejooservicemain.service.JwtProviderService
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Profile(value = ["dev", "test"])
@RequestMapping("/api/test/token")
@RestController
class TestTokenController(
    private val jwtProviderService: JwtProviderService,
) {
    @GetMapping
    fun getToken(): GetTokenResponse {
        val testUserId: Long = 1
        return GetTokenResponse(token = jwtProviderService.generateAuthToken(testUserId))
    }
}