package com.thejoo.thejooservicemain.repository

import com.thejoo.thejooservicemain.entity.TransactionHistory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TransactionHistoryRepository : JpaRepository<TransactionHistory, Long> {
    fun findByMembershipIdOrderByCreatedAtDesc(membershipId: Long, pageable: Pageable): Page<TransactionHistory>
    fun findTopByMembershipIdOrderByIdDesc(membershipId: Long): Optional<TransactionHistory>
}
