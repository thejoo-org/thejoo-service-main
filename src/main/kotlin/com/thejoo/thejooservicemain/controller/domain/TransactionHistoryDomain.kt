package com.thejoo.thejooservicemain.controller.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.thejoo.thejooservicemain.entity.TransactionStatus
import com.thejoo.thejooservicemain.entity.TransactionType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "거래 내역 리스트 응답")
@JsonInclude
data class TransactionHistoryGetResponse(
    @Schema(description = "거래 내역 ID")
    val id: Long,
    @Schema(description = "거래 내역 타입")
    val type: TransactionType,
    @Schema(description = "거래 내역 상태")
    val status: TransactionStatus,
    @Schema(description = "프로모션 ID")
    val promotionId: Long,
    @Schema(description = "적립 포인트")
    val addedPoint: Long,
    @Schema(description = "포인트 스냅샷")
    val pointSnapshot: Long,
    @Schema(description = "업장 ID")
    val storeId: Long,
    @Schema(description = "멤버쉽 ID")
    val membershipId: Long,
    @Schema(description = "거래 내역 데이터")
    var data: Map<String, Any>?,
    @Schema(description = "거래 내역 생성일시")
    val createdAt: LocalDateTime,
    @Schema(description = "거래 내역 수정일시")
    val updatedAt: LocalDateTime,
)