package com.thejoo.thejooservicemain.repository

import com.thejoo.thejooservicemain.entity.Store
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface StoreRepository : JpaRepository<Store, Long> {
    fun findByIdAndOwnerId(id: Long, ownerId: Long): Optional<Store>
}
