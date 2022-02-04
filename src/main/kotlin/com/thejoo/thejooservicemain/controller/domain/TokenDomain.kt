package com.thejoo.thejooservicemain.controller.domain

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "토큰 응답")
@JsonInclude
data class SimpleTokenResponse(
    @Schema(description = "JWT 토큰")
    val token: String,
)
