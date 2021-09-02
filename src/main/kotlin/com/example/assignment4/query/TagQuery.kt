package com.example.assignment4.query

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.example.assignment4.entity.Tag
import com.example.assignment4.security.Security
import com.example.assignment4.service.TagService
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TagQuery : GraphQLQueryResolver {

    @Autowired
    private val tagService: TagService? = null

    fun getTag(id: Int, env: DataFetchingEnvironment): Tag? {
        Security().authenticate(env)
        return this.tagService!!.getTag(id)
    }

    fun getTags(): MutableList<Tag> {
        return this.tagService!!.getTags();
    }
}