package com.example.assignment4.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import org.springframework.security.core.authority.SimpleGrantedAuthority

class MyUserPrincipal : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val auth = ArrayList<SimpleGrantedAuthority>()
        auth.add(SimpleGrantedAuthority("User"))
        return auth
    }

    override fun isEnabled(): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return true;
    }

    override fun getUsername(): String {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return "TestUsername";
    }

    override fun isCredentialsNonExpired(): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return false;
    }

    override fun getPassword(): String {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return "test";
    }

    override fun isAccountNonExpired(): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return true;
    }

    override fun isAccountNonLocked(): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return true;
    }
}