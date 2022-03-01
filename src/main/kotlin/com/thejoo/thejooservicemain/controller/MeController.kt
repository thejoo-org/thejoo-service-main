package com.thejoo.thejooservicemain.controller

import com.thejoo.thejooservicemain.controller.domain.MembershipGetResponse
import com.thejoo.thejooservicemain.controller.domain.MembershipIndexResponse
import com.thejoo.thejooservicemain.controller.domain.SimpleTokenResponse
import com.thejoo.thejooservicemain.controller.domain.StoreGetResponse
import com.thejoo.thejooservicemain.controller.domain.TransactionHistoryGetResponse
import com.thejoo.thejooservicemain.controller.domain.UserProfileResponse
import com.thejoo.thejooservicemain.entity.Membership
import com.thejoo.thejooservicemain.entity.TransactionHistory
import com.thejoo.thejooservicemain.entity.User
import com.thejoo.thejooservicemain.infrastructure.annotation.PrincipalUser
import com.thejoo.thejooservicemain.service.JwtProviderService
import com.thejoo.thejooservicemain.service.MembershipService
import com.thejoo.thejooservicemain.service.TransactionHistoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "내 정보")
@RequestMapping("/api/me")
@RestController
class MeController(
    private val membershipService: MembershipService,
    private val jwtProviderService: JwtProviderService,
    private val transactionHistoryService: TransactionHistoryService,
) {
    @Operation(summary = "유저 기본 정보", description = "유저 기본 정보 조회")
    @GetMapping("/profile")
    fun getProfile(@PrincipalUser user: User): UserProfileResponse = user.toUserProfileResponse()

    @Operation(summary = "유저 QR 토큰", description = "유저 QR 렌더링을 위한 토큰")
    @GetMapping("/tokens/for-qr")
    fun getTokenForPromotion(@PrincipalUser user: User) =
        user
            .let(jwtProviderService::generateUserReadToken)
            .let { SimpleTokenResponse(token = it) }

    @Operation(
        summary = "내 멤버쉽 리스트 조회",
        description = "내 멤버쉽 리스트 조회",
    )
    @GetMapping("/memberships")
    fun getMemberships(@PrincipalUser user: User, pageable: Pageable): Page<MembershipIndexResponse> = user
        .let { membershipService.getMembershipsForUser(user = it, pageable = pageable) }
        .map { it.toMembershipIndexResponse() }

    @Operation(
        summary = "내 멤버쉽 상세 조회",
        description = "내 멤버쉽 상세 조회",
    )
    @GetMapping("/memberships/{membership_id}")
    fun getMembership(
        @PrincipalUser user: User,
        @Parameter(description = "멤버쉽 ID") @PathVariable("membership_id") membershipId: Long
    ): MembershipGetResponse =
        membershipService.getMembershipWithEntityGraphByIdForUser(membershipId, user).toMembershipGetResponse()

    @Operation(
        summary = "내 멤버쉽 거래 내역 리스트 조회",
        description = "내 멤버쉽 거래 내역 리스트 조회",
    )
    @GetMapping("/memberships/{membership_id}/transaction-histories")
    fun getMembershipTransactionHistories(
        @PrincipalUser user: User,
        @Parameter(description = "멤버쉽 ID") @PathVariable("membership_id") membershipId: Long,
        pageable: Pageable,
    ): Page<TransactionHistoryGetResponse> = user
        .let { membershipService.getMembershipByIdForUser(id = membershipId, user = it) }
        .let { membership -> transactionHistoryService.getTransactionHistoriesForMembership(membership, pageable) }
        .map { it.toTransactionHistoryGetResponse() }

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
