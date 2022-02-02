package com.thejoo.thejooservicemain.repository

import com.thejoo.thejooservicemain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long>