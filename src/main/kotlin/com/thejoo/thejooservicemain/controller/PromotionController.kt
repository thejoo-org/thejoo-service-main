package com.thejoo.thejooservicemain.controller

import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.Claim
import com.thejoo.thejooservicemain.config.security.subjectAsLong
import com.thejoo.thejooservicemain.config.token.TokenConstants
import com.thejoo.thejooservicemain.controller.domain.ApplyPromotionRequest
import com.thejoo.thejooservicemain.controller.domain.ApplyPromotionResponse
import com.thejoo.thejooservicemain.controller.domain.GeneratePromotionTokenRequest
import com.thejoo.thejooservicemain.controller.domain.SimpleTokenResponse
import com.thejoo.thejooservicemain.entity.User
import com.thejoo.thejooservicemain.infrastructure.advice.ExceptionCode
import com.thejoo.thejooservicemain.infrastructure.advice.TheJooException
import com.thejoo.thejooservicemain.infrastructure.annotation.OwnerController
import com.thejoo.thejooservicemain.infrastructure.annotation.PrincipalUser
import com.thejoo.thejooservicemain.service.ApplyPromotionFacadeService
import com.thejoo.thejooservicemain.service.JwtProviderService
import com.thejoo.thejooservicemain.service.JwtVerifierService
import com.thejoo.thejooservicemain.service.PromotionService
import com.thejoo.thejooservicemain.service.StoreService
import com.thejoo.thejooservicemain.service.domain.ApplyPromotionResult
import com.thejoo.thejooservicemain.service.domain.ApplyPromotionSpec
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.util.UUID

@Tag(name = "프로모션")
@RequestMapping("/api/promotions")
@OwnerController
class PromotionController(
    private val promotionService: PromotionService,
    private val storeService: StoreService,
    private val applyPromotionFacadeService: ApplyPromotionFacadeService,
    private val jwtProviderService: JwtProviderService,
    private val jwtVerifierService: JwtVerifierService,
) {
    private val log = LoggerFactory.getLogger(PromotionController::class.java)

    @Operation(summary = "프로모션 토큰 생성", description = "프로모션 토큰 생성")
    @PostMapping("/generate-token")
    fun generateToken(
        @RequestBody generatePromotionTokenRequest: GeneratePromotionTokenRequest,
        @PrincipalUser owner: User,
    ): SimpleTokenResponse = promotionService.getPromotionById(generatePromotionTokenRequest.promotionId)
        .also { promotion -> storeService.getStoreForUserById(promotion.storeId, owner) }
        .let { promotion -> jwtProviderService.generatePromotionToken(owner, promotion) }
        .let { SimpleTokenResponse(token = it) }

    @Operation(summary = "프로모션 적용", description = "프로모션 적용")
    @PostMapping("/apply")
    fun applyPromotion(
        @RequestBody applyPromotionRequest: ApplyPromotionRequest,
        @PrincipalUser owner: User,
    ): ApplyPromotionResponse {
        return try {
            val userId = jwtVerifierService
                .verifyUserReadToken(applyPromotionRequest.targetUserToken)
                .subjectAsLong()
            val decodedPromotionToken = jwtVerifierService
                .verifyPromotionToken(token = applyPromotionRequest.promotionToken, ownerId = owner.id!!)
            val promotionUUID = decodedPromotionToken.getClaim(TokenConstants.CLAIM_NAME_PROMOTION_UUID)
                .let(Claim::asString)
                .let(UUID::fromString)
            val spec = ApplyPromotionSpec(
                targetUserId = userId,
                targetPromotionId = decodedPromotionToken.subjectAsLong(),
                owner = owner,
                promotionUUID = promotionUUID,
            )
            applyPromotionFacadeService.execute(spec).toResponse()
        } catch (e: JWTVerificationException) {
            log.error("Error occurred while verifying JWT", e)
            throw TheJooException.ofBadRequest(ExceptionCode.INVALID_TOKEN_PROVIDED)
        }
    }

    private fun ApplyPromotionResult.toResponse() = ApplyPromotionResponse(
        userId = user.id!!,
        membershipId = membership.id!!,
        transactionHistoryId = transactionHistory.id!!,
        addedPoint = promotion.point,
        currentPoint = membership.point,
        isNewlyRegistered = membership.isNewlyRegistered,
    )
}
