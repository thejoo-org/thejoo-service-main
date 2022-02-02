package com.thejoo.thejooservicemain.entity

import javax.persistence.*

@Entity
@Table(name = "memberships")
class Membership(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column
    val userId: Long,
    @Column
    val storeId: Long,
    @Column
    var point: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    var store: Store? = null,
    @Transient
    val isNewlyRegistered: Boolean = false,
): AbstractAuditableEntity() {
    constructor(userId: Long, storeId: Long): this(
        userId = userId,
        storeId = storeId,
        point = 0,
        isNewlyRegistered = true,
    )

    fun addPoint(pointToAdd: Long): Membership {
        this.point += pointToAdd
        return this
    }

    override fun toString(): String =
        "Membership(id=$id, userId=$userId, storeId=$storeId, point=$point, isNewlyRegistered=$isNewlyRegistered)"
}
