package com.thejoo.thejooservicemain.entity

import javax.persistence.*

@Entity
@Table(name = "promotions")
class Promotion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column
    val storeId: Long,
    @Column
    var point: Long = 0,
    @Column
    val title: String,
    @Column
    val description: String,
) : AbstractAuditableEntity() {

    override fun toString(): String =
        "Promotion(id=$id, storeId=$storeId, point=$point, title='$title', description='$description')"
}
