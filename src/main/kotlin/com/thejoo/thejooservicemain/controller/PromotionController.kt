package com.thejoo.thejooservicemain.controller

import com.thejoo.thejooservicemain.controller.domain.PromotionReadRequest
import com.thejoo.thejooservicemain.service.PromotionService
import com.thejoo.thejooservicemain.service.UserService
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RequestMapping("/api/promotions")
@RestController
class PromotionController(
    private val userService: UserService,
    private val promotionService: PromotionService,
) {
    @GetMapping("/{id}")
    fun getPromotionById(@PathVariable id: Long, principal: Principal) {
        userService.getUserById(principal.name)
    }


    @PostMapping("/read")
    fun readForUser(promotionReadRequest: PromotionReadRequest) {

    }
}
