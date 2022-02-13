package com.thejoo.thejooservicemain.entity

class AdminUser(
    name: String,
    email: String,
    private val password: String,
) : User(name = name, email = email, roles = listOf(Role.ROLE_ADMIN)) {
    override fun getUsername(): String = email

    override fun getPassword(): String = password

    fun isAuthenticated(email: String, password: String) = this.email == email && this.password == password
}
