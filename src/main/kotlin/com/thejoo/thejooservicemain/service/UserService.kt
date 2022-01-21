package com.thejoo.thejooservicemain.service

import com.thejoo.thejooservicemain.entity.User
import com.thejoo.thejooservicemain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getAllUsers(): List<User> = userRepository.findAll()
    fun getUserById(id: Long): User = userRepository.getById(id)
    fun getUserById(id: String): User = getUserById(id.toLong())
}
