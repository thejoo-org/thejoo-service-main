package com.thejoo.thejooservicemain.entity

import com.thejoo.thejooservicemain.infrastructure.advice.ExceptionCode
import com.thejoo.thejooservicemain.infrastructure.advice.TheJooException
import javax.persistence.*

@Entity
@Table(name = "stores")
class Store(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column
    val ownerId: Long,
    @Column
    val name: String,
    @Column
    var email: String? = null,
): AbstractAuditableEntity() {
    fun validateManageableBy(userId: Long) {
        if (isManageableBy(userId))
            throw TheJooException.ofBadRequest(ExceptionCode.STORE_OWNER_MISMATCH)
    }

    private fun isManageableBy(userId: Long) = ownerId != userId

    override fun toString(): String = "Store(id=$id, ownerId=$ownerId, name='$name', email=$email)"
}