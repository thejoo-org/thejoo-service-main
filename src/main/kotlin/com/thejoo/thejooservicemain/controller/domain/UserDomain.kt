package com.thejoo.thejooservicemain.controller.domain

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude
data class UserProfileResponse(
    val id: Long,
    val name: String,
    val email: String,
    val createdAt: LocalDateTime,
)