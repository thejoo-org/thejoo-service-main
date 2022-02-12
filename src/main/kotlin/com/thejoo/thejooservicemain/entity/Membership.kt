package com.thejoo.thejooservicemain.entity

import org.hibernate.annotations.JoinFormula
import javax.persistence.*

@Entity
@Table(name = "memberships")
class Membership(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column
    val userId: Long,
    @Column(name = "store_id")
    val storeId: Long,
    @Column
    var point: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    var store: Store? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinFormula("(" +
            "SELECT tx.id " +
            "FROM {h-schema}transaction_histories tx " +
            "WHERE tx.membership_id = id AND tx.status = 'SUCCESS' AND tx.type = 'APPLY' " +
            "ORDER BY created_at DESC " +
            "LIMIT 1" +
            ")")
    var latestApplyTransactionHistory: TransactionHistory? = null,
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
        point += pointToAdd
        return this
    }

    override fun toString(): String =
        "Membership(id=$id, userId=$userId, storeId=$storeId, point=$point, isNewlyRegistered=$isNewlyRegistered)"
}
