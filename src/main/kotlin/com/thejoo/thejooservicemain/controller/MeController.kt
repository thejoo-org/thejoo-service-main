package com.thejoo.thejooservicemain.controller

import com.thejoo.thejooservicemain.service.JwtProviderService
import com.thejoo.thejooservicemain.service.QrCodeProviderService
import com.thejoo.thejooservicemain.service.UserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RequestMapping("/api/me/qr-code")
@RestController
class MeController(
    private val userService: UserService,
    private val jwtProviderService: JwtProviderService,
    private val qrCodeProviderService: QrCodeProviderService,
) {
    @GetMapping(path = ["/for-promotion"], produces = [MediaType.IMAGE_PNG_VALUE])
    fun getQrCodeForUser(principal: Principal): ByteArray? {
        return principal.name
            .let(userService::getUserById)
            .let(jwtProviderService::generatePromotionReadToken)
            .let(qrCodeProviderService::generateQrCodeForReadableToken)
    }
}