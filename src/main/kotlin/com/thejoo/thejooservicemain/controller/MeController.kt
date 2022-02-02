package com.thejoo.thejooservicemain.controller

import com.thejoo.thejooservicemain.config.security.nameAsLong
import com.thejoo.thejooservicemain.controller.domain.SimpleTokenResponse
import com.thejoo.thejooservicemain.service.JwtProviderService
import com.thejoo.thejooservicemain.service.MembershipService
import com.thejoo.thejooservicemain.service.QrCodeProviderService
import com.thejoo.thejooservicemain.service.UserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RequestMapping("/api/me")
@RestController
class MeController(
    private val userService: UserService,
    private val membershipService: MembershipService,
    private val jwtProviderService: JwtProviderService,
    private val qrCodeProviderService: QrCodeProviderService,
) {
    @GetMapping(path = ["/qr-code/for-read-promotion"], produces = [MediaType.IMAGE_PNG_VALUE])
    fun getQrCodeForUser(principal: Principal): ByteArray? =
        principal.name
            .let(userService::getUserById)
            .let(jwtProviderService::generateUserReadToken)
            .let(qrCodeProviderService::generateQrCodeForReadableToken)

    @GetMapping("/token/for-read-promotion")
    fun getTokenForPromotion(principal: Principal) =
        principal.name
            .let(userService::getUserById)
            .let(jwtProviderService::generateUserReadToken)
            .let { SimpleTokenResponse(token = it) }

    @GetMapping("/memberships")
    fun getMemberships(principal: Principal) =
        userService.getUserById(principal.nameAsLong())
            .let(membershipService::getMembershipsForUser)
}