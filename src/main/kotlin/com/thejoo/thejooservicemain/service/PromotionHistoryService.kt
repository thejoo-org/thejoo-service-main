package com.thejoo.thejooservicemain.service

import com.thejoo.thejooservicemain.infrastructure.advice.ExceptionCode
import com.thejoo.thejooservicemain.infrastructure.advice.TheJooException
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class PromotionHistoryService {
    private val log = LoggerFactory.getLogger(PromotionService::class.java)
    private val historyHolder: MutableSet<History> = mutableSetOf()

    fun validate(uuid: UUID) {
        if (!historyHolder.contains(History(uuid)))
            return
        log.info("Promotion already applied for promotionUUID = $uuid")
        throw TheJooException.ofBadRequest(ExceptionCode.ALREADY_APPLIED_PROMOTION)
    }

    fun register(uuid: UUID) {
        if (historyHolder.add(History(uuid))) {
            log.info("Added history for promotionUUID= $uuid")
        } else {
            log.info("History already exists for promotionUUID = $uuid")
        }
    }

    @Scheduled(fixedDelayString = "PT15M")
    fun scheduledEvictExpired() = evictExpired()

    fun evictExpired() {
        val now = LocalDateTime.now()
        log.info("Evicting expired promotion history holder for timestamp = $now...")
        historyHolder.removeAll { it.timestamp.isBefore(now) }
    }

    fun evict(uuid: UUID) = historyHolder.remove(History(uuid))

    private inner class History(
        val uuid: UUID,
        val timestamp: LocalDateTime,
    ) {
        constructor(uuid: UUID) : this(uuid = uuid, timestamp = LocalDateTime.now().plusHours(1))

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as History

            if (uuid != other.uuid) return false

            return true
        }

        override fun hashCode(): Int {
            return uuid.hashCode()
        }
    }
}
