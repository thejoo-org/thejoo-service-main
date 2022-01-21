package com.thejoo.thejooservicemain.service

import com.thejoo.thejooservicemain.entity.Promotion
import com.thejoo.thejooservicemain.entity.Store
import com.thejoo.thejooservicemain.entity.User
import com.thejoo.thejooservicemain.repository.PromotionRepository
import com.thejoo.thejooservicemain.repository.StoreRepository
import com.thejoo.thejooservicemain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class PromotionService(
    private val userRepository: UserRepository,
    private val storeRepository: StoreRepository,
    private val promotionRepository: PromotionRepository,
) {
    fun getPromotionByIdAndOwnerId(promotionId: Long, ownerId: Long): Promotion =
        userRepository.getById(ownerId)
            .let(User::id)!!
            .let(storeRepository::getByOwnerId)
            .let(Store::id)!!
            .let(promotionRepository::getByStoreId)

    fun getPromotionById(promotionId: Long): Promotion = promotionRepository.getById(promotionId)
}