package com.thejoo.thejooservicemain.controller.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude
data class GeneratePromotionTokenRequest(
    @JsonProperty("promotion_id") val promotionId: Long,
)

@JsonInclude
data class ApplyPromotionRequest(
    @JsonProperty("target_user_token") val targetUserToken: String,
    @JsonProperty("promotion_token") val promotionToken: String,
)

@JsonInclude
data class ApplyPromotionResponse(
    @JsonProperty("user_id") val userId: Long,
    @JsonProperty("membership_id") val membershipId: Long,
    @JsonProperty("added_point") val addedPoint: Long,
    @JsonProperty("current_point") val currentPoint: Long,
    @JsonProperty("is_newly_registered") val isNewlyRegistered: Boolean,
)