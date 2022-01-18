package com.thejoo.thejooservicemain.controller

import com.thejoo.thejooservicemain.service.JwtProviderService
import com.thejoo.thejooservicemain.service.QrCodeProviderService
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Profile(value = ["dev", "test",])
@RequestMapping("/api/test/qr-code")
@RestController
class TestQrCodeController(
    private val jwtProviderService: JwtProviderService,
    private val qrCodeProviderService: QrCodeProviderService,
) {
    @GetMapping(produces = [MediaType.IMAGE_PNG_VALUE])
    fun getQrCodeForUser(principal: Principal): ByteArray? {
        return jwtProviderService.generateReadOnlyQrToken(principal.name.toLong())
            .let(qrCodeProviderService::generateQrCodeForReadableToken)
    }
}