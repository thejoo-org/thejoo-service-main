package com.thejoo.thejooservicemain.repository

import com.thejoo.thejooservicemain.entity.Promotion
import org.springframework.data.jpa.repository.JpaRepository

interface PromotionRepository : JpaRepository<Promotion, Long> {
    fun getByStoreId(storeId: Long): Promotion
}
