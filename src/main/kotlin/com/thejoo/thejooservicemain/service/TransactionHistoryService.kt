package com.thejoo.thejooservicemain.service

import com.thejoo.thejooservicemain.entity.TransactionHistory
import com.thejoo.thejooservicemain.entity.TransactionStatus
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
        membershipId: Long,
        promotionId: Long,
        addedPoint: Long,
        pointSnapshot: Long,
        storeId: Long,
        data: Map<String, Any>? = null,
    ): Future<TransactionHistory> = TransactionHistory(
        type = type,
        membershipId = membershipId,
        status = TransactionStatus.UNKNOWN,
        promotionId = promotionId,
        storeId = storeId,
        addedPoint = addedPoint,
        pointSnapshot = pointSnapshot,
        data = data,
    ).let(transactionHistoryRepository::save).let(::AsyncResult)

    @Async
    fun updateTransactionHistoryAsync(
        transactionHistory: TransactionHistory,
        status: TransactionStatus,
    ): Future<TransactionHistory> {
        transactionHistory.status = status
        return transactionHistoryRepository.save(transactionHistory)
            .let(::AsyncResult)
    }
}