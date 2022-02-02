package com.thejoo.thejooservicemain.entity

import com.thejoo.thejooservicemain.infrastructure.converter.ArrayStringConverter
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
    @Convert(converter = ArrayStringConverter::class)
    @Column
    val roles: List<String> = listOf(),
): AbstractAuditableEntity(), UserDetails {
    constructor(id: Long, roles: List<Role>) : this(id = id, roles = roles.map(Role::name), name = "", email = "")

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        roles.map(::SimpleGrantedAuthority).toMutableSet()

    override fun getPassword(): String = id.toString()

    override fun getUsername(): String = id.toString()

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun toString(): String =
        "User(id=$id, name='$name', email='$email', roles=$roles)"

    companion object {
        val TEMPORARY_DEFAULT_AUTHORITY = SimpleGrantedAuthority("ROLE_USER")
    }
}