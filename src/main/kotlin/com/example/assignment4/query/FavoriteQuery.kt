package com.example.assignment4.query

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.example.assignment4.entity.Event
import com.example.assignment4.entity.Favorite
import com.example.assignment4.entity.User
import com.example.assignment4.security.Security
import com.example.assignment4.service.EventService
import com.example.assignment4.service.FavoriteService
import com.example.assignment4.service.UserService
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class FavoriteQuery : GraphQLQueryResolver {

    @Autowired
    private val favoriteService: FavoriteService? = null

    fun getFavorite(id: Int, env: DataFetchingEnvironment): Favorite? {
        Security().authenticate(env)
        return this.favoriteService!!.getFavorite(id)
    }

}