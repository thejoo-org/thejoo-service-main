package com.thejoo.thejooservicemain.service

import com.thejoo.thejooservicemain.entity.TransactionHistory
import com.thejoo.thejooservicemain.entity.TransactionType
import com.thejoo.thejooservicemain.repository.TransactionHistoryRepository
import org.springframework.stereotype.Service

@Service
class TransactionHistoryService(
    private val transactionHistoryRepository: TransactionHistoryRepository,
) {
    fun createTransactionHistory(
        type: TransactionType,
        userId: Long,
        promotionId: Long,
        addedPoint: Long,
        pointSnapshot: Long,
        storeId: Long,
        data: Map<String, Any>? = null,
    ) = TransactionHistory(
        type = type,
        userId = userId,
        promotionId = promotionId,
        storeId = storeId,
        addedPoint = addedPoint,
        pointSnapshot = pointSnapshot,
        data = data,
    ).let(transactionHistoryRepository::save)
}