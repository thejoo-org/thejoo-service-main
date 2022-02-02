package com.thejoo.thejooservicemain.controller.domain

import java.time.LocalDateTime

data class MembershipIndexResponse(
    val id: Long,
    val userId: Long,
    val point: Long,
    val joinedAt: LocalDateTime,
)