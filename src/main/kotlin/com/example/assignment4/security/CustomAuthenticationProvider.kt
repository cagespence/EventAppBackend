package com.example.assignment4.security

import com.example.assignment4.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class CustomAuthenticationProvider: AuthenticationProvider {

    @Autowired
    private val userService: UserService? = null

    @Throws(Exception::class)
    override fun authenticate(authentication: Authentication?): Authentication?  {
        val email = authentication!!.name
        val password = authentication.credentials.toString()

        val user = userService!!.getUserByEmail(email)

        return if (user == null || !(BCryptPasswordEncoder().matches(password, user.getPassword()))) {
            println("Incorrect credentials");
            throw Exception("Incorrect credentials")
        } else {
            // Here use the user object to only check if the user exists in the database if not null use his login ( principal ) and password
            UsernamePasswordAuthenticationToken(email, password)
        }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication!! == UsernamePasswordAuthenticationToken::class.java
    }

}