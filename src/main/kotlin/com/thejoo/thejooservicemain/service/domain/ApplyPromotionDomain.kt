package com.thejoo.thejooservicemain.service.domain

import com.thejoo.thejooservicemain.entity.Membership
import com.thejoo.thejooservicemain.entity.Promotion
import com.thejoo.thejooservicemain.entity.TransactionHistory
import com.thejoo.thejooservicemain.entity.User
import java.util.*

data class ApplyPromotionSpec(
    val targetUserId: Long,
    val owner: User,
    val targetPromotionId: Long,
    val promotionUUID: UUID,
)

data class ApplyPromotionResult(
    val user: User,
    val membership: Membership,
    val promotion: Promotion,
    val transactionHistory: TransactionHistory,
)
