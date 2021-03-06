package com.thejoo.thejooservicemain.service

import com.thejoo.thejooservicemain.entity.Store
import com.thejoo.thejooservicemain.entity.User
import com.thejoo.thejooservicemain.infrastructure.advice.TheJooException
import com.thejoo.thejooservicemain.repository.StoreRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class StoreService(
    private val storeRepository: StoreRepository,
) {
    fun getStoreById(storeId: Long): Store = getMaybeStoreById(storeId)
        .orElseThrow { TheJooException.ofEntityNotFound("Store NOT found for id = $storeId") }

    fun getStoreForUserById(storeId: Long, user: User): Store = storeRepository.findByIdAndOwnerId(storeId, user.id!!)
        .orElseThrow { TheJooException.ofEntityNotFound("Store NOT found for id = $storeId, userId = ${user.id}") }

    fun getMaybeStoreById(storeId: Long): Optional<Store> = storeRepository.findById(storeId)
}
