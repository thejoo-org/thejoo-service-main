package com.thejoo.thejooservicemain.controller.admin

import com.fasterxml.jackson.annotation.JsonInclude
import com.thejoo.thejooservicemain.controller.domain.SimpleTokenResponse
import com.thejoo.thejooservicemain.service.admin.AdminAuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/admin/auth")
@RestController
class AuthController(
    private val adminAuthService: AdminAuthService
) {
    @PostMapping
    fun signIn(@RequestBody signInRequest: SignInRequest) =
        adminAuthService.signIn(signInRequest.email, signInRequest.password)
            .let(::SimpleTokenResponse)

    @JsonInclude
    data class SignInRequest(
        val email: String,
        val password: String,
    )
}
