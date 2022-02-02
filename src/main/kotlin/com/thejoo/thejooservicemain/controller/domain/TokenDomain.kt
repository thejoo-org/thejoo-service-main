package com.thejoo.thejooservicemain.controller.domain

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude
data class SimpleTokenResponse(
    val token: String,
)
