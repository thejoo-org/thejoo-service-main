package com.thejoo.thejooservicemain.controller.admin

import com.thejoo.thejooservicemain.controller.domain.UserProfileResponse
import com.thejoo.thejooservicemain.entity.User
import com.thejoo.thejooservicemain.infrastructure.advice.AdminController
import com.thejoo.thejooservicemain.service.UserService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/api/admin/users")
@AdminController
class UserController(
    private val userService: UserService,
) {
    @GetMapping
    fun getUsers(pageable: Pageable) = userService.getAllUsersWithPage(pageable).map { it.toUserProfileResponse() }

    private fun User.toUserProfileResponse() =
        UserProfileResponse(
            id = id!!,
            name = name,
            email = email,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
}
