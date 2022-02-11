package com.thejoo.thejooservicemain.controller

import com.thejoo.thejooservicemain.config.security.nameAsLong
import com.thejoo.thejooservicemain.controller.domain.*
import com.thejoo.thejooservicemain.entity.Membership
import com.thejoo.thejooservicemain.entity.User
import com.thejoo.thejooservicemain.service.JwtProviderService
import com.thejoo.thejooservicemain.service.MembershipService
import com.thejoo.thejooservicemain.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
        principal.name.let(userService::getUserById).toUserProfileResponse()

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

    @Operation(
        summary = "내 멤버쉽 상세 조회",
        description = "내 멤버쉽 상세 조회",
    )
    @GetMapping("/memberships/{membership_id}")
    fun getMembership(
        principal: Principal,
        @Parameter(description = "멤버쉽 ID") @PathVariable("membership_id") membershipId: Long
    ) =
        userService.getUserById(principal.nameAsLong())
            .let { membershipService.getMembershipByIdForUser(membershipId, it) }.toMembershipGetResponse()

    private fun Membership.toMembershipIndexResponse() =
        MembershipIndexResponse(
            id = this.id!!,
            userId = this.userId,
            storeId = this.storeId,
            storeName = this.store?.name,
            point = this.point,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
        )

    private fun Membership.toMembershipGetResponse() =
        MembershipGetResponse(
            id = this.id!!,
            userId = this.userId,
            storeId = this.storeId,
            store = this.store?.let {
                StoreGetResponse(
                    id = it.id!!,
                    ownerId = it.ownerId,
                    name = it.name,
                    email = it.email,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt,
                )
            },
            point = this.point,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            latestApplyTransactionHistory = this.latestApplyTransactionHistory?.let {
                TransactionHistoryGetResponse(
                    id = it.id!!,
                    type = it.type,
                    status = it.status!!,
                    promotionId = it.promotionId!!,
                    addedPoint = it.addedPoint,
                    pointSnapshot = it.pointSnapshot,
                    storeId = it.storeId,
                    membershipId = it.membershipId,
                    data = it.data,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt,
                )
            },
        )

    private fun User.toUserProfileResponse() =
        UserProfileResponse(
            id = this.id!!,
            name = this.name,
            email = this.email,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
        )
}