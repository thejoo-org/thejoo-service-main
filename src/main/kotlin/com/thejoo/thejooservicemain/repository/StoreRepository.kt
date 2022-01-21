package com.thejoo.thejooservicemain.repository

import com.thejoo.thejooservicemain.entity.Store
import org.springframework.data.jpa.repository.JpaRepository

interface StoreRepository: JpaRepository<Store, Long> {
    fun getByOwnerId(ownerId: Long): Store
}