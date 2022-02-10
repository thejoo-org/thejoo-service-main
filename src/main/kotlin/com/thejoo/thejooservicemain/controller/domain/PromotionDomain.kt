package com.thejoo.thejooservicemain.controller.domain

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "프로모션 토큰 생성 요청")
@JsonInclude
data class GeneratePromotionTokenRequest(
    @Schema(description = "프로모션 ID")
    val promotionId: Long,
)

@Schema(description = "프로모션 적용 요청")
@JsonInclude
data class ApplyPromotionRequest(
    @Schema(description = "적용 대상 유저 토큰")
    val targetUserToken: String,
    @Schema(description = "프로모션 토큰")
    val promotionToken: String,
)

@Schema(description = "프로모션 적용 응답")
@JsonInclude
data class ApplyPromotionResponse(
    @Schema(description = "적용 대상 유저 ID")
    val userId: Long,
    @Schema(description = "적용 대상 멤버쉽 ID")
    val membershipId: Long,
    @Schema(description = "거래 내역 ID")
    val transactionHistoryId: Long,
    @Schema(description = "적용된 포인트")
    val addedPoint: Long,
    @Schema(description = "적용 대상 유저의 멤버쉽 총 포인트")
    val currentPoint: Long,
    @Schema(description = "적용 대상 멤버쉽 생성 여부")
    val isNewlyRegistered: Boolean,
)