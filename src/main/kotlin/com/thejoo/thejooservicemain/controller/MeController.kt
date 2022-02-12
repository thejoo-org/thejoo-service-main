package com.thejoo.thejooservicemain.controller

import com.thejoo.thejooservicemain.config.security.nameAsLong
import com.thejoo.thejooservicemain.controller.domain.*
import com.thejoo.thejooservicemain.entity.Membership
import com.thejoo.thejooservicemain.entity.TransactionHistory
import com.thejoo.thejooservicemain.entity.User
import com.thejoo.thejooservicemain.service.JwtProviderService
import com.thejoo.thejooservicemain.service.MembershipService
import com.thejoo.thejooservicemain.service.TransactionHistoryService
import com.thejoo.thejooservicemain.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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
    private val transactionHistoryService: TransactionHistoryService,
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
    fun getMemberships(principal: Principal, pageable: Pageable): Page<MembershipIndexResponse> =
        userService.getUserById(principal.nameAsLong())
            .let { membershipService.getMembershipsForUser(user = it, pageable = pageable) }
            .map { it.toMembershipIndexResponse() }

    @Operation(
        summary = "내 멤버쉽 상세 조회",
        description = "내 멤버쉽 상세 조회",
    )
    @GetMapping("/memberships/{membership_id}")
    fun getMembership(
        principal: Principal,
        @Parameter(description = "멤버쉽 ID") @PathVariable("membership_id") membershipId: Long
    ): MembershipGetResponse {
        return userService.getUserById(principal.nameAsLong())
            .let { membershipService.getMembershipWithEntityGraphByIdForUser(membershipId, it) }.toMembershipGetResponse()
    }

    @Operation(
        summary = "내 멤버쉽 거래 내역 리스트 조회",
        description = "내 멤버쉽 거래 내역 리스트 조회",
    )
    @GetMapping("/memberships/{membership_id}/transaction-histories")
    fun getMembershipTransactionHistories(
        principal: Principal,
        @Parameter(description = "멤버쉽 ID") @PathVariable("membership_id") membershipId: Long,
        pageable: Pageable,
    ): Page<TransactionHistoryGetResponse> {
        return userService.getUserById(principal.nameAsLong())
            .let { membershipService.getMembershipByIdForUser(id = membershipId, user = it) }
            .let { transactionHistoryService.getTransactionHistoriesForMembership(membership = it, pageable = pageable) }
            .map { it.toTransactionHistoryGetResponse() }
    }

    private fun Membership.toMembershipIndexResponse() =
        MembershipIndexResponse(
            id = id!!,
            userId = userId,
            storeId = storeId,
            storeName = store?.name,
            point = point,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

    private fun Membership.toMembershipGetResponse() =
        MembershipGetResponse(
            id = id!!,
            userId = userId,
            storeId = storeId,
            store = store?.let {
                StoreGetResponse(
                    id = it.id!!,
                    ownerId = it.ownerId,
                    name = it.name,
                    email = it.email,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt,
                )
            },
            point = point,
            createdAt = createdAt,
            updatedAt = updatedAt,
            latestApplyTransactionHistory = latestApplyTransactionHistory?.toTransactionHistoryGetResponse(),
        )

    private fun TransactionHistory.toTransactionHistoryGetResponse() =
        TransactionHistoryGetResponse(
            id = id!!,
            type = type,
            status = status!!,
            promotionId = promotionId!!,
            addedPoint = addedPoint,
            pointSnapshot = pointSnapshot,
            storeId = storeId,
            membershipId = membershipId,
            data = data,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

    private fun User.toUserProfileResponse() =
        UserProfileResponse(
            id = id!!,
            name = name,
            email = email,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
}