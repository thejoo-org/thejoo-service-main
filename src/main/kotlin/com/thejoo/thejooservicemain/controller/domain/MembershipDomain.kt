package com.thejoo.thejooservicemain.controller.domain

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "멤버쉽 리스트 응답")
@JsonInclude
data class MembershipIndexResponse(
    @Schema(description = "멤버쉽 ID")
    val id: Long,
    @Schema(description = "유저 ID")
    val userId: Long,
    @Schema(description = "업장 ID")
    val storeId: Long,
    @Schema(description = "업장명")
    var storeName: String? = "",
    @Schema(description = "멤버쉽 포인트")
    val point: Long,
    @Schema(description = "멤버쉽 가입일시")
    val createdAt: LocalDateTime,
    @Schema(description = "멤버쉽 수정일시")
    val updatedAt: LocalDateTime,
)

@Schema(description = "멤버쉰 상세 조회 응답")
@JsonInclude
data class MembershipGetResponse(
    @Schema(description = "멤버쉽 ID")
    val id: Long,
    @Schema(description = "유저 ID")
    val userId: Long,
    @Schema(description = "업장 ID")
    val storeId: Long,
    @Schema(description = "업장 상세")
    var store: StoreGetResponse? = null,
    @Schema(description = "멤버쉽 포인트")
    val point: Long,
    @Schema(description = "멤버쉽 가입일시")
    val createdAt: LocalDateTime,
    @Schema(description = "멤버쉽 수정일시")
    val updatedAt: LocalDateTime,
    @Schema(description = "최근 적립 거래 내역")
    var latestApplyTransactionHistory: TransactionHistoryGetResponse? = null,
)