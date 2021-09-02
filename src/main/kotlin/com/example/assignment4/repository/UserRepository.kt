package com.example.assignment4.repository

import com.example.assignment4.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String): User?
    fun findByEmailAndPassword(email: String, password: String): Optional<User>
}