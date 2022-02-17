package com.thejoo.thejooservicemain.entity

import com.fasterxml.jackson.annotation.JsonInclude
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

    fun toPromotionSnapshot() = PromotionSnapshot(title, description)

    override fun toString(): String =
        "Promotion(id=$id, storeId=$storeId, point=$point, title='$title', description='$description')"
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PromotionSnapshot(
    var title: String? = null,
    val description: String? = null,
)
