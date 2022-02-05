package com.thejoo.thejooservicemain.service

import com.thejoo.thejooservicemain.entity.TransactionHistory
import com.thejoo.thejooservicemain.entity.TransactionType
import com.thejoo.thejooservicemain.repository.TransactionHistoryRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Service
import java.util.concurrent.Future

@Service
class TransactionHistoryService(
    private val transactionHistoryRepository: TransactionHistoryRepository,
) {
    @Async
    fun createTransactionHistoryAsync(
        type: TransactionType,
        userId: Long,
        promotionId: Long,
        addedPoint: Long,
        pointSnapshot: Long,
        storeId: Long,
        data: Map<String, Any>? = null,
    ): Future<TransactionHistory> = TransactionHistory(
        type = type,
        userId = userId,
        promotionId = promotionId,
        storeId = storeId,
        addedPoint = addedPoint,
        pointSnapshot = pointSnapshot,
        data = data,
    ).let(transactionHistoryRepository::save).let(::AsyncResult)
}