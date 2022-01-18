package com.thejoo.thejooservicemain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column
    var name: String? = null,
    @Column
    var email: String? = null,
    @CreatedDate
    @Column
    var createdAt: LocalDateTime? = null,
    @LastModifiedDate
    @Column
    var updatedAt: LocalDateTime? = null,
    @Version
    @Column
    var version: Long? = null,
): UserDetails {
    fun getIdInString(): String = id.toString()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = arrayListOf(TEMPORARY_DEFAULT_AUTHORITY)

    override fun getPassword(): String = id.toString()

    override fun getUsername(): String = id.toString()

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    companion object {
        val TEMPORARY_DEFAULT_AUTHORITY = SimpleGrantedAuthority("ROLE_USER")
    }
}