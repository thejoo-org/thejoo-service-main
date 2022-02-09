package com.thejoo.thejooservicemain.controller

import com.thejoo.thejooservicemain.config.security.nameAsLong
import com.thejoo.thejooservicemain.controller.domain.MembershipIndexResponse
import com.thejoo.thejooservicemain.controller.domain.SimpleTokenResponse
import com.thejoo.thejooservicemain.controller.domain.UserProfileResponse
import com.thejoo.thejooservicemain.entity.Membership
import com.thejoo.thejooservicemain.entity.User
import com.thejoo.thejooservicemain.service.JwtProviderService
import com.thejoo.thejooservicemain.service.MembershipService
import com.thejoo.thejooservicemain.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "내 정보")
@RequestMapping("/api/me")
@RestController
class MeController(
    private val userService: UserService,
    private val membershipService: MembershipService,
    private val jwtProviderService: JwtProviderService,
) {
    @Operation(summary = "유저 기본 정보", description = "유저 기본 정보 조회")
    @GetMapping("/profile")
    fun getProfile(principal: Principal): UserProfileResponse =
        principal.name
            .let(userService::getUserById)
            .let { it.toUserProfileResponse() }

    @Operation(summary = "유저 QR 토큰", description = "유저 QR 렌더링을 위한 토큰")
    @GetMapping("/tokens/for-qr")
    fun getTokenForPromotion(principal: Principal) =
        principal.name
            .let(userService::getUserById)
            .let(jwtProviderService::generateUserReadToken)
            .let { SimpleTokenResponse(token = it) }

    @Operation(
        summary = "내 멤버쉽 리스트 조회",
        description = "내 멤버쉽 리스트 조회",
    )
    @GetMapping("/memberships")
    fun getMemberships(principal: Principal): List<MembershipIndexResponse> =
        userService.getUserById(principal.nameAsLong())
            .let(membershipService::getMembershipsForUser)
            .map { it.toMembershipIndexResponse() }

    private fun Membership.toMembershipIndexResponse() =
        MembershipIndexResponse(
            id = this.id!!,
            userId = this.userId,
            storeId = this.storeId,
            storeName = this.store?.name,
            point = this.point,
            joinedAt = this.createdAt,
        )

    private fun User.toUserProfileResponse() =
        UserProfileResponse(
            id = this.id!!,
            name = this.name,
            email = this.email,
            createdAt = this.createdAt,
        )
}