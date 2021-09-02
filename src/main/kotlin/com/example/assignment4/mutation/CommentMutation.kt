package com.example.assignment4.mutation

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.example.assignment4.entity.Comment
import com.example.assignment4.security.Security
import com.example.assignment4.service.CommentService
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CommentMutation : GraphQLMutationResolver {

    @Autowired
    private val commentService: CommentService? = null

    fun createComment(userId: Int, eventId: Int, comment: String, env: DataFetchingEnvironment): Comment? {
        Security().authenticate(env)
        return this.commentService!!.createComment(userId, eventId, comment)
    }

}