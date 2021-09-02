package com.example.assignment4.query

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.example.assignment4.entity.Comment
import com.example.assignment4.entity.Event
import com.example.assignment4.security.Security
import com.example.assignment4.service.CommentService
import com.example.assignment4.service.EventService
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CommentQuery : GraphQLQueryResolver {

    @Autowired
    private val commentService: CommentService? = null

    fun getComment(id: Int, env: DataFetchingEnvironment): Comment? {
        Security().authenticate(env)
        return this.commentService!!.getComment(id)
    }
}