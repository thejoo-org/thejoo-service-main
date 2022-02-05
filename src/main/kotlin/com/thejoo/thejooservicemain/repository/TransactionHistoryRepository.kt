package com.thejoo.thejooservicemain.repository

import com.thejoo.thejooservicemain.entity.TransactionHistory
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionHistoryRepository: JpaRepository<TransactionHistory, Long>