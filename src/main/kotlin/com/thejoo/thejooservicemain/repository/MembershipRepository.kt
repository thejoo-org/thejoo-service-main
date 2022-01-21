package com.thejoo.thejooservicemain.repository

import com.thejoo.thejooservicemain.entity.Membership
import org.springframework.data.jpa.repository.JpaRepository

interface MembershipRepository: JpaRepository<Membership, Long>