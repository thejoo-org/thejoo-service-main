package com.thejoo.thejooservicemain.entity

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
): AbstractAuditableEntity()