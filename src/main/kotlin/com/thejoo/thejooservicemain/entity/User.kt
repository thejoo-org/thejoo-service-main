package com.thejoo.thejooservicemain.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column
    val name: String,
    @Column
    val email: String,
    @ElementCollection(targetClass = Role::class)
    @Column
    val roles: List<Role>? = listOf(),
): AbstractAuditableEntity(), UserDetails {
    constructor(id: Long, roles: List<Role>) : this(id = id, roles = roles, name = "", email = "")

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        roles!!
            .map(Role::name)
            .map(::SimpleGrantedAuthority)
            .toMutableSet()

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