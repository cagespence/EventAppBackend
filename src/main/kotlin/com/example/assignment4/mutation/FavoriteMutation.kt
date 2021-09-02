package com.example.assignment4.mutation

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.example.assignment4.entity.Favorite
import com.example.assignment4.security.Security
import com.example.assignment4.service.FavoriteService
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class FavoriteMutation: GraphQLMutationResolver {

    @Autowired
    private val favoriteService: FavoriteService? = null

    fun addFavorite(userId: Int, eventId: Int, env: DataFetchingEnvironment): Favorite {
        Security().authenticate(env)
        return this.favoriteService!!.addFavorite(userId, eventId)
    }

    fun removeFavorite(userId: Int, eventId: Int, env: DataFetchingEnvironment): Boolean {
        Security().authenticate(env)
        return this.favoriteService!!.removeFavorite(userId, eventId)
    }
}