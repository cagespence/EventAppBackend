package com.example.assignment4.model

import org.springframework.security.core.userdetails.UserDetails
import com.example.assignment4.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService : UserDetailsService {

//    @Autowired
//    private val userRepository: UserRepository? = null

    override fun loadUserByUsername(email: String): UserDetails {
//        val user = userRepository!!.findByEmail(email)
        return MyUserPrincipal()
    }
}
