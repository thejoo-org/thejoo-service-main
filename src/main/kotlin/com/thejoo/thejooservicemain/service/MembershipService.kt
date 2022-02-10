package com.thejoo.thejooservicemain.service

import com.thejoo.thejooservicemain.entity.Membership
import com.thejoo.thejooservicemain.entity.Store
import com.thejoo.thejooservicemain.entity.User
import com.thejoo.thejooservicemain.repository.MembershipRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.Future
import javax.transaction.Transactional

@Service
class MembershipService(
    private val membershipRepository: MembershipRepository,
) {
    private val log = LoggerFactory.getLogger(MembershipService::class.java)

    fun getMaybeMembershipByUserAndStore(user: User, store: Store): Optional<Membership> =
        membershipRepository.findByUserIdAndStoreId(user.id!!, store.id!!)

    @Transactional
    fun getOrRegisterMembership(user: User, store: Store): Membership =
        getMaybeMembershipByUserAndStore(user, store)
            .orElseGet {
                log.info("Membership NOT found for user = $user...")
                registerMembershipForUser(user, store)
            }

    @Async
    @Transactional
    fun getOrRegisterMembershipAsync(user: User, store: Store): Future<Membership> =
        getMaybeMembershipByUserAndStore(user, store)
            .orElseGet {
                log.info("Membership NOT found for user = $user...")
                registerMembershipForUser(user, store)
            }.let(::AsyncResult)

    fun getMembershipsForUser(user: User): List<Membership> =
        membershipRepository.findAllEntityGraphByUserId(user.id!!)

    fun registerMembershipForUser(user: User, store: Store): Membership =
        save(Membership(userId = user.id!!, storeId = store.id!!))
            .also { log.info("Successfully registered membership for user = $user, membership = $it") }

    fun addPointToMembership(membership: Membership, pointToAdd: Long) =
        save(membership.addPoint(pointToAdd))

    fun save(membership: Membership) = membershipRepository.save(membership)
}