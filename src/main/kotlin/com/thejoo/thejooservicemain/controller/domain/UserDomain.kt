package com.thejoo.thejooservicemain.controller.domain

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "유저 기본 정보 응답")
@JsonInclude
data class UserProfileResponse(
    @Schema(description = "유저 ID")
    val id: Long,
    @Schema(description = "유저 이름")
    val name: String,
    @Schema(description = "유저 이메일")
    val email: String,
    @Schema(description = "유저 생성일시")
    val createdAt: LocalDateTime,
    @Schema(description = "유저 수정일시")
    val updatedAt: LocalDateTime,
)
