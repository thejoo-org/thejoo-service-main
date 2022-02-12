package com.thejoo.thejooservicemain.controller

import com.fasterxml.jackson.annotation.JsonInclude
import com.thejoo.thejooservicemain.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "[개발용] 유저 조회")
@Profile(value = ["dev", "test", "sandbox"])
@RequestMapping("/api/users")
@RestController
class TestUserController(
    private val userService: UserService
) {
    @Operation(hidden = true)
    @GetMapping
    fun getUsers(): List<TempUserResponse> {
        return userService.getAllUsers()
            .map { TempUserResponse(id = it.id!!, name = it.name,) }
    }

    @JsonInclude
    data class TempUserResponse(
        val id: Long,
        val name: String,
    )
}
