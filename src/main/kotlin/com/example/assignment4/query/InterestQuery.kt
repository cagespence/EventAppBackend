package com.example.assignment4.query

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.example.assignment4.entity.Event
import com.example.assignment4.entity.Favorite
import com.example.assignment4.entity.Interest
import com.example.assignment4.entity.User
import com.example.assignment4.security.Security
import com.example.assignment4.service.*
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class InterestQuery : GraphQLQueryResolver {

    @Autowired
    private val interestService: InterestService? = null

    fun getInterest(id: Int, env: DataFetchingEnvironment): Interest? {
        Security().authenticate(env)
        return this.interestService!!.getInterest(id)
    }

}