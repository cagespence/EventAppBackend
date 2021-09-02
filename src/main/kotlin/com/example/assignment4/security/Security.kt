package com.example.assignment4.security

import graphql.schema.DataFetchingEnvironment
import graphql.schema.DataFetchingEnvironmentImpl
import graphql.servlet.context.GraphQLServletContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component

public class Security {


    public fun authenticate(env: DataFetchingEnvironment) {
        val jwtTokenUtil = JwtTokenUtil()

        if (env == null) {
            throw AccessDeniedException("No headers are set!")
        } else {
            val context = (env.getContext() as GraphQLServletContext)

            val token = context.httpServletRequest.getHeader("Authorization")
            val email = context.httpServletRequest.getHeader("UserEmail")

            if (token == null && email == null) {
                throw AccessDeniedException("User not authenticated")
            }

            if (token == null || email == null) {
                throw AccessDeniedException("Headers are not set correctly")
            }

            if (!(jwtTokenUtil!!.validateToken(token, email)!!)) {
                throw AccessDeniedException("Invalid token!")
            }
        }
    }
}