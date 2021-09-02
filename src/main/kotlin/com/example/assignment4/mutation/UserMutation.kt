package com.example.assignment4.mutation

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.example.assignment4.entity.User
import com.example.assignment4.model.AuthData
import com.example.assignment4.model.SignInPayload
import com.example.assignment4.security.CustomAuthenticationProvider
import com.example.assignment4.security.JwtTokenUtil
import com.example.assignment4.security.Unsecured
import com.example.assignment4.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component


@Component
class UserMutation : GraphQLMutationResolver {

    @Autowired
    private val authenticationManager: CustomAuthenticationProvider? = null

    @Autowired
    private val jwtTokenUtil: JwtTokenUtil? = null

    @Autowired
    private val userService: UserService? = null

    fun createUser(name: String, dob: String, bio: String = "", authData: AuthData): User {
        return this.userService!!.createUser(name, dob, bio, authData)
    }

    @Throws(Exception::class)
    @Unsecured
    fun signInUser(authData: AuthData): SignInPayload? {
        val responsePayload = SignInPayload()

        val foundUser =  this.userService!!.getUserByEmail(authData.email)

        if (foundUser != null) {    // user found
            print("authdata email is: ${authData.email}  and authdata pass is: ${authData.password} " )

            authenticationManager?.authenticate(UsernamePasswordAuthenticationToken(authData.email, authData.password))

            val token = jwtTokenUtil?.generateToken(authData.email)

            if (token != null) {
                responsePayload.token = token
                responsePayload.user = foundUser
            } else {
                throw Exception("Something went wrong with creating a token!")
            }
        } else {
            // error handling - this user was not found in DB
            throw Exception("This user does not exist!")
        }

        return responsePayload
    }
}