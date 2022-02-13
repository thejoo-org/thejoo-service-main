package com.thejoo.thejooservicemain.service.admin

import com.thejoo.thejooservicemain.entity.AdminUser
import com.thejoo.thejooservicemain.infrastructure.advice.TheJooException
import com.thejoo.thejooservicemain.service.JwtProviderService
import org.springframework.stereotype.Service

@Service
class AdminAuthService(
    private val adminUser: AdminUser,
    private val jwtProviderService: JwtProviderService,
) {
    fun signIn(email: String, password: String): String {
        if (!adminUser.isAuthenticated(email, password))
            throw TheJooException.ofUnauthorized("Admin sign-in denied")

        return jwtProviderService.generateAdminAuthToken(adminUser)
    }
}
