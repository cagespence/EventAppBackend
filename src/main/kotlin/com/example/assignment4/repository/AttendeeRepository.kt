package com.example.assignment4.repository

import com.example.assignment4.entity.Attendee
import com.example.assignment4.entity.Event
import com.example.assignment4.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AttendeeRepository : JpaRepository<Attendee, Int> {
    fun findByUserAndEvent(user: User, event: Event): Attendee?
}