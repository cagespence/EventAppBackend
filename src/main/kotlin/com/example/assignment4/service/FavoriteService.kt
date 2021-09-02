package com.example.assignment4.service

import com.example.assignment4.entity.Event
import com.example.assignment4.entity.Favorite
import com.example.assignment4.entity.User
import com.example.assignment4.errors.CustomException
import com.example.assignment4.repository.FavoriteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FavoriteService constructor() {

    @Autowired
    private var favoriteRepository: FavoriteRepository? = null

    @Autowired
    private val userService: UserService? = null;

    @Autowired
    private val eventService: EventService? = null

    @Transactional
    fun addFavorite(userId: Int, eventId: Int): Favorite {
        val newFav = Favorite()
        val retrievedUser: User? = (userService!!.getUser(userId))
        val retrievedEvent: Event? = (eventService!!.getEvent(eventId))


        if (retrievedEvent != null && retrievedUser != null) {
            val foundFavorite = this.favoriteRepository!!.findByUserAndAndEvent(retrievedUser, retrievedEvent)

            if (foundFavorite != null) {
                throw CustomException("Favorite exists already!")
            }

            newFav.setEvent(retrievedEvent)
            newFav.setUser(retrievedUser)
        } else {
            throw CustomException("User or event does not exist!")
        }

        return this.favoriteRepository!!.save(newFav)
    }

    @Transactional
    fun removeFavorite(userId: Int, eventId: Int): Boolean {
        val retrievedUser: User? = (userService!!.getUser(userId))
        val retrievedEvent: Event? = (eventService!!.getEvent(eventId))

        if (retrievedEvent != null && retrievedUser != null) {
            val foundFavorite = this.favoriteRepository!!.findByUserAndAndEvent(retrievedUser, retrievedEvent)
            if (foundFavorite != null) {
                this.favoriteRepository!!.delete(foundFavorite)
                return true
            } else {
                throw CustomException("Favorite was not found.")
            }
        } else {
            throw CustomException("User or Event can not be found.")
        }
    }

    @Transactional
    public fun getFavorite(id: Int): Favorite? {
        return this.favoriteRepository!!.findById(id).orElse(null)
    }

}