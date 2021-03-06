package com.thejoo.thejooservicemain.entity

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*

@Entity
@Table(name = "transaction_histories")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
class TransactionHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Enumerated(EnumType.STRING)
    @Column
    val type: TransactionType,
    @Enumerated(EnumType.STRING)
    @Column
    var status: TransactionStatus?,
    @Column
    var promotionId: Long? = null,
    @Column
    var addedPoint: Long = 0,
    @Column
    val pointSnapshot: Long,
    @Column
    val storeId: Long,
    @Column
    val membershipId: Long,
    @Type(type = "jsonb")
    @Column(name = "data", columnDefinition = "jsonb")
    val data: PromotionSnapshot,
) : AbstractAuditableEntity()

enum class TransactionStatus {
    UNKNOWN, SUCCESS, FAIL
}
