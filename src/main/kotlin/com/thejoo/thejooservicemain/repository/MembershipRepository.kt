package com.thejoo.thejooservicemain.repository

import com.thejoo.thejooservicemain.entity.Membership
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MembershipRepository: JpaRepository<Membership, Long> {
    fun findByUserIdAndStoreId(userId: Long, storeId: Long): Optional<Membership>

    fun findAllByUserId(userId: Long): List<Membership>

    @EntityGraph(attributePaths = ["store"])
    fun findAllEntityGraphByUserId(userId: Long): List<Membership>
}