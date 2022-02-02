package com.thejoo.thejooservicemain.controller

import com.thejoo.thejooservicemain.controller.domain.SimpleTokenResponse
import com.thejoo.thejooservicemain.service.JwtProviderService
import com.thejoo.thejooservicemain.service.UserService
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Profile(value = ["dev", "test"])
@RequestMapping("/api/test/token")
@RestController
class TestTokenController(
    private val userService: UserService,
    private val jwtProviderService: JwtProviderService,
) {
    @GetMapping("/{id}")
    fun getToken(@PathVariable id: Long): SimpleTokenResponse =
        userService.getUserById(id)
            .let(jwtProviderService::generateAuthToken)
            .let { SimpleTokenResponse(token = it) }
}