package com.example.assignment4.mutation


import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.example.assignment4.entity.Interest
import com.example.assignment4.model.Tags
import com.example.assignment4.security.Security
import com.example.assignment4.service.InterestService
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class InterestMutation : GraphQLMutationResolver {

    @Autowired
    private val interestService: InterestService? = null

    fun addInterest(userId: Int, tags: Tags, env: DataFetchingEnvironment): List<Interest?> {
        Security().authenticate(env)
        return this.interestService!!.addInterest(userId, tags)
    }



//    fun removeInterest(tags: Array<Int>): Boolean {
//        return this.interestService!!.removeInterests(like)
//    }
}