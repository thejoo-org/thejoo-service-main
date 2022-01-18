package com.thejoo.thejooservicemain.repository

import com.thejoo.thejooservicemain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long>