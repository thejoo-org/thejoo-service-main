package com.thejoo.thejooservicemain.entity

import lombok.Getter
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass
import javax.persistence.Version

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AbstractAuditableEntity {
    @CreatedDate
    @Column
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    @Column
    lateinit var updatedAt: LocalDateTime

    @Version
    @Column
    var version: Long = 0
}