package com.thejoo.thejooservicemain.controller.domain

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema
@JsonInclude
data class MembershipIndexResponse(
    val id: Long,
    val userId: Long,
    val storeId: Long,
    var storeName: String? = "",
    val point: Long,
    val joinedAt: LocalDateTime,
)