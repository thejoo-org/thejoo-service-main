package com.thejoo.thejooservicemain.service

import com.thejoo.thejooservicemain.entity.Promotion
import com.thejoo.thejooservicemain.entity.Store
import com.thejoo.thejooservicemain.infrastructure.advice.TheJooException
import com.thejoo.thejooservicemain.repository.PromotionRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PromotionService(
    private val promotionRepository: PromotionRepository,
) {
    fun getPromotionsForStore(store: Store): List<Promotion> = promotionRepository.findByStoreId(store.id!!)

    fun getMaybePromotionById(promotionId: Long): Optional<Promotion> = promotionRepository.findById(promotionId)

    fun getPromotionById(promotionId: Long): Promotion =
        getMaybePromotionById(promotionId)
            .orElseThrow { TheJooException.ofEntityNotFound("Promotion NOT found for id = $promotionId") }
}
