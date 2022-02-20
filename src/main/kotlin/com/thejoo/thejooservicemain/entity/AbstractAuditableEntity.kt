package com.thejoo.thejooservicemain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass
import javax.persistence.Version

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AbstractAuditableEntity {
    @CreatedDate
    @Column(name = "created_at")
    open lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    @Column(name = "updated_at")
    open lateinit var updatedAt: LocalDateTime

    @Version
    @Column(name = "version")
    open var version: Long = 0
}
