package com.thejoo.thejooservicemain.service

import com.thejoo.thejooservicemain.entity.User
import com.thejoo.thejooservicemain.infrastructure.advice.TheJooException
import com.thejoo.thejooservicemain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository
) : UserDetailsService {
    fun getAllUsers(): List<User> = userRepository.findAll()
    fun getMaybeUserById(id: Long): Optional<User> = userRepository.findById(id)
    fun getUserById(id: Long): User = getMaybeUserById(id)
        .orElseThrow { TheJooException.ofEntityNotFound("User NOT found for id = $id") }
    fun getUserById(id: String): User = getUserById(id.toLong())
    fun save(user: User): User = userRepository.save(user)

    /**
     * TODO: This is implemented to suppress default user password generation
     */
    override fun loadUserByUsername(username: String?): UserDetails = getUserById(username!!)
}
