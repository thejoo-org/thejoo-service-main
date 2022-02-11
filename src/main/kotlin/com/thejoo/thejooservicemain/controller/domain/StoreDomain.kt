package com.thejoo.thejooservicemain.controller.domain

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "업장 상세 조회 응답")
@JsonInclude
data class StoreGetResponse(
    @Schema(description = "업장 ID")
    val id: Long,
    @Schema(description = "업주 ID")
    val ownerId: Long,
    @Schema(description = "업장명")
    val name: String,
    @Schema(description = "업장 대표 이메일")
    var email: String?,
    @Schema(description = "업장 생성일시")
    val createdAt: LocalDateTime,
    @Schema(description = "업장 수정일시")
    val updatedAt: LocalDateTime,
)
