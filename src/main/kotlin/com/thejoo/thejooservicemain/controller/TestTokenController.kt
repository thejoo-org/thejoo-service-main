package com.thejoo.thejooservicemain.controller

import com.thejoo.thejooservicemain.controller.domain.SimpleTokenResponse
import com.thejoo.thejooservicemain.service.JwtProviderService
import com.thejoo.thejooservicemain.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "[개발용] 유저 토큰")
@Profile(value = ["dev", "test", "sandbox"])
@RequestMapping("/api/test/tokens")
@RestController
class TestTokenController(
    private val userService: UserService,
    private val jwtProviderService: JwtProviderService,
) {
    @Operation(summary = "유저 인증 토큰 생성", description = "유저 인증 토큰 생성")
    @GetMapping("/{userId}")
    fun getToken(@PathVariable userId: Long): SimpleTokenResponse =
        userService.getUserById(userId)
            .let(jwtProviderService::generateAuthToken)
            .let { SimpleTokenResponse(token = it) }
}
