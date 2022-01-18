package com.thejoo.thejooservicemain.service

import com.thejoo.thejooservicemain.entity.User
import com.thejoo.thejooservicemain.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getAllUsers(): List<User> = userRepository.findAll()
    fun getUserById(id: Long): Optional<User> = userRepository.findById(id)
    fun getUserById(id: String): Optional<User> = userRepository.findById(id.toLong())
}