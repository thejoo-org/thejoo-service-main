package com.thejoo.thejooservicemain.controller

import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import com.thejoo.thejooservicemain.config.security.nameAsLong
import com.thejoo.thejooservicemain.config.security.subjectAsLong
import com.thejoo.thejooservicemain.config.token.TokenConstants
import com.thejoo.thejooservicemain.controller.domain.ApplyPromotionRequest
import com.thejoo.thejooservicemain.controller.domain.ApplyPromotionResponse
import com.thejoo.thejooservicemain.controller.domain.GeneratePromotionTokenRequest
import com.thejoo.thejooservicemain.controller.domain.SimpleTokenResponse
import com.thejoo.thejooservicemain.infrastructure.advice.ExceptionCode
import com.thejoo.thejooservicemain.infrastructure.advice.TheJooException
import com.thejoo.thejooservicemain.service.*
import com.thejoo.thejooservicemain.service.domain.ApplyPromotionResult
import com.thejoo.thejooservicemain.service.domain.ApplyPromotionSpec
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.*

@Tag(name = "프로모션")
@RequestMapping("/api/promotions")
@RestController
class PromotionController(
    private val userService: UserService,
    private val promotionService: PromotionService,
    private val applyPromotionFacadeService: ApplyPromotionFacadeService,
    private val jwtProviderService: JwtProviderService,
    private val jwtVerifierService: JwtVerifierService,
) {
    private val log = LoggerFactory.getLogger(PromotionController::class.java)

    @Operation(summary = "프로모션 토큰 생성", description = "프로모션 토큰 생성")
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/generate-token")
    fun generateToken(
        @RequestBody generatePromotionTokenRequest: GeneratePromotionTokenRequest,
        principal: Principal,
    ): SimpleTokenResponse {
        val owner = userService.getUserById(principal.name)
        return promotionService.getPromotionById(generatePromotionTokenRequest.promotionId)
            .let { jwtProviderService.generatePromotionToken(owner, it) }
            .let { SimpleTokenResponse(token = it) }
    }

    @Operation(summary = "프로모션 적용", description = "프로모션 적용")
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/apply")
    fun applyPromotion(
        @RequestBody applyPromotionRequest: ApplyPromotionRequest,
        principal: Principal,
    ): ApplyPromotionResponse {
        return try {
            val userId = jwtVerifierService
                .verifyUserReadToken(applyPromotionRequest.targetUserToken)
                .subjectAsLong()
            val decodedPromotionToken = jwtVerifierService
                .verifyPromotionToken(token = applyPromotionRequest.promotionToken, ownerId = principal.nameAsLong())
            val promotionUUID = decodedPromotionToken.getClaim(TokenConstants.CLAIM_NAME_PROMOTION_UUID)
                .let(Claim::asString)
                .let(UUID::fromString)
            val spec = ApplyPromotionSpec(
                targetUserId = userId,
                targetPromotionId = decodedPromotionToken.subjectAsLong(),
                ownerId = principal.nameAsLong(),
                promotionUUID = promotionUUID,
            )
            applyPromotionFacadeService.execute(spec).toResponse()
        } catch (e: JWTVerificationException) {
            log.error("Error occurred while verifying JWT", e)
            throw TheJooException.ofBadRequest(ExceptionCode.INVALID_TOKEN_PROVIDED)
        }
    }

    private fun ApplyPromotionResult.toResponse() = ApplyPromotionResponse(
        userId = this.user.id!!,
        membershipId = this.membership.id!!,
        transactionHistoryId = this.transactionHistory.id!!,
        addedPoint = this.promotion.point,
        currentPoint = this.membership.point,
        isNewlyRegistered = this.membership.isNewlyRegistered,
    )
}
