package com.thejoo.thejooservicemain.controller.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "프로모션 토큰 생성 요청")
@JsonInclude
data class GeneratePromotionTokenRequest(
    @Schema(description = "프로모션 ID")
    @JsonProperty("promotion_id")
    val promotionId: Long,
)

@Schema(description = "프로모션 적용 요청")
@JsonInclude
data class ApplyPromotionRequest(
    @Schema(description = "적용 대상 유저 토큰")
    @JsonProperty("target_user_token")
    val targetUserToken: String,
    @Schema(description = "프로모션 토큰")
    @JsonProperty("promotion_token")
    val promotionToken: String,
)

@Schema(description = "프로모션 적용 응답")
@JsonInclude
data class ApplyPromotionResponse(
    @Schema(description = "적용 대상 유저 ID")
    @JsonProperty("user_id")
    val userId: Long,
    @Schema(description = "적용 대상 멤버쉽 ID")
    @JsonProperty("membership_id")
    val membershipId: Long,
    @Schema(description = "적용된 포인트")
    @JsonProperty("added_point")
    val addedPoint: Long,
    @Schema(description = "적용 대상 유저의 멤버쉽 총 포인트")
    @JsonProperty("current_point")
    val currentPoint: Long,
    @Schema(description = "적용 대상 멤버쉽 생성 여부")
    @JsonProperty("is_newly_registered")
    val isNewlyRegistered: Boolean,
)