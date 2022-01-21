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
    val point: Long,
): AbstractAuditableEntity();