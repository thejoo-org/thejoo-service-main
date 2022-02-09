package com.thejoo.thejooservicemain.repository

import com.thejoo.thejooservicemain.entity.TransactionHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TransactionHistoryRepository: JpaRepository<TransactionHistory, Long> {
    fun findTopByMembershipIdOrderByIdDesc(membershipId: Long): Optional<TransactionHistory>
}
