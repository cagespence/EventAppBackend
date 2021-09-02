package com.example.assignment4.repository

import com.example.assignment4.entity.Event
import com.example.assignment4.entity.Favorite
import com.example.assignment4.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FavoriteRepository : JpaRepository<Favorite, Int> {
    fun findByUserAndAndEvent(user: User, event: Event): Favorite?
}