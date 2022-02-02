package com.thejoo.thejooservicemain.service

import com.thejoo.thejooservicemain.service.domain.ApplyPromotionResult
import com.thejoo.thejooservicemain.service.domain.ApplyPromotionSpec
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ApplyPromotionFacadeService(
    private val promotionService: PromotionService,
    private val promotionHistoryService: PromotionHistoryService,
    private val membershipService: MembershipService,
    private val userService: UserService,
    private val storeService: StoreService,
) {
    @Transactional
    fun execute(applyPromotionSpec: ApplyPromotionSpec): ApplyPromotionResult {
        promotionHistoryService.validate(applyPromotionSpec.promotionUUID)
        promotionHistoryService.register(uuid = applyPromotionSpec.promotionUUID)
        return try {
            val targetPromotion = promotionService.getPromotionById(applyPromotionSpec.targetPromotionId)
            val targetStore = storeService.getStoreById(targetPromotion.storeId)
                .also { it.validateManageableBy(applyPromotionSpec.ownerId) }
            val targetUser = userService.getUserById(applyPromotionSpec.targetUserId)
            val membership = membershipService.getOrRegisterMembership(targetUser, targetStore)
                .let { membershipService.addPointToMembership(membership = it, targetPromotion.point) }
            ApplyPromotionResult(user = targetUser, membership = membership, promotion = targetPromotion)
        } catch (e: Exception) {
            promotionHistoryService.evict(applyPromotionSpec.promotionUUID)
            throw e
        }
    }
}