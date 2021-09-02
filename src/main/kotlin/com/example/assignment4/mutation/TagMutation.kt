package com.example.assignment4.mutation

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.example.assignment4.entity.Favorite
import com.example.assignment4.service.FavoriteService
import com.example.assignment4.service.TagService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class TagMutation : GraphQLMutationResolver {

    @Autowired
    private val tagService: TagService? = null

//    fun addUserTag(user: Int, event: Int): Favorite {
//        return this.tagService!!.addUserTag(user, event)
//    }
}