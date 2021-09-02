package com.example.assignment4.query

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.example.assignment4.entity.User
import com.example.assignment4.security.Security
import com.example.assignment4.security.Unsecured
import com.example.assignment4.service.UserService
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserQuery : GraphQLQueryResolver {

    @Autowired
    private val userService: UserService? = null

    fun getUser(id: Int, env: DataFetchingEnvironment): User? {
        Security().authenticate(env)
        return this.userService!!.getUser(id)
    }

    @Unsecured
    fun isEmailAvailable(email: String): Boolean {
        val foundUser =  this.userService!!.getUserByEmail(email);
        return foundUser == null
    }

}