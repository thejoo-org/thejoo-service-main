package com.thejoo.thejooservicemain.controller

import com.fasterxml.jackson.annotation.JsonInclude
import com.thejoo.thejooservicemain.service.UserService
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Profile(value = ["dev", "test"])
@RequestMapping("/api/users")
@RestController
class TestUserController(
    private val userService: UserService
) {
    @GetMapping
    fun getUsers(): List<TempUserResponse> {
        return userService.getAllUsers()
            .map { TempUserResponse(id = it.id, name = it.name,) }
    }

    @JsonInclude
    data class TempUserResponse(
        val id: Long,
        val name: String?,
    )
}