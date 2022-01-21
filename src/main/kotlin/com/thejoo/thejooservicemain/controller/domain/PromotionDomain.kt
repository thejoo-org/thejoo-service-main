package com.thejoo.thejooservicemain.controller.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude
data class PromotionReadRequest(
    @JsonProperty("user_token") val userToken: String,
    @JsonProperty("promotion_token") val promotionToken: String,
)
