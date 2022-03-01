package com.thejoo.thejooservicemain.controller.store

import com.thejoo.thejooservicemain.entity.User
import com.thejoo.thejooservicemain.infrastructure.annotation.OwnerController
import com.thejoo.thejooservicemain.infrastructure.annotation.PrincipalUser
import com.thejoo.thejooservicemain.service.PromotionService
import com.thejoo.thejooservicemain.service.StoreService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/api/stores/{store_id}/promotions")
@OwnerController
class StorePromotionController(
    private val promotionService: PromotionService,
    private val storeService: StoreService,
) {
    @GetMapping
    fun getPromotions(@PathVariable("store_id") storeId: Long, @PrincipalUser user: User) =
        storeService.getStoreForUserById(storeId, user)
            .let(promotionService::getPromotionsForStore)
}
