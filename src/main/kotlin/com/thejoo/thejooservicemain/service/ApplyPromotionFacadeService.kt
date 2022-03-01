package com.thejoo.thejooservicemain.service

import com.thejoo.thejooservicemain.entity.TransactionStatus
import com.thejoo.thejooservicemain.entity.TransactionType
import com.thejoo.thejooservicemain.service.domain.ApplyPromotionResult
import com.thejoo.thejooservicemain.service.domain.ApplyPromotionSpec
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ApplyPromotionFacadeService(
    private val promotionService: PromotionService,
    private val promotionHistoryService: PromotionHistoryService,
    private val transactionHistoryService: TransactionHistoryService,
    private val membershipService: MembershipService,
    private val userService: UserService,
    private val storeService: StoreService,
) {
    private val log = LoggerFactory.getLogger(ApplyPromotionFacadeService::class.java)

    @Transactional
    fun execute(applyPromotionSpec: ApplyPromotionSpec): ApplyPromotionResult {
        promotionHistoryService.validate(applyPromotionSpec.promotionUUID)
        val targetPromotion = promotionService.getPromotionById(applyPromotionSpec.targetPromotionId)
        val targetStore = storeService.getStoreById(targetPromotion.storeId)
            .also { it.validateManageableBy(applyPromotionSpec.owner.id!!) }
        val targetUser = userService.getUserById(applyPromotionSpec.targetUserId)
        val targetMembership = membershipService.getOrRegisterMembershipAsync(targetUser, targetStore).get()
        val transactionHistory = transactionHistoryService.createTransactionHistoryAsync(
            type = TransactionType.APPLY,
            membershipId = targetMembership.id!!,
            promotionId = targetPromotion.id!!,
            addedPoint = targetPromotion.point,
            pointSnapshot = targetMembership.point + targetPromotion.point,
            storeId = targetStore.id!!,
            data = targetPromotion.toPromotionSnapshot(),
        ).get()
        return try {
            promotionHistoryService.register(uuid = applyPromotionSpec.promotionUUID)
            membershipService.addPointToMembership(membership = targetMembership, targetPromotion.point)
            transactionHistoryService.updateTransactionHistoryAsync(transactionHistory, TransactionStatus.SUCCESS).get()
            ApplyPromotionResult(
                user = targetUser,
                membership = targetMembership,
                promotion = targetPromotion,
                transactionHistory = transactionHistory,
            )
        } catch (e: Exception) {
            log.error("Error occurred while applying promotion ${applyPromotionSpec.promotionUUID}", e)
            promotionHistoryService.evict(applyPromotionSpec.promotionUUID)
            transactionHistoryService.updateTransactionHistoryAsync(transactionHistory, TransactionStatus.FAIL).get()
            throw e
        }
    }
}
