package com.example.assignment4.repository

import com.example.assignment4.entity.Event
import com.example.assignment4.entity.Tag
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : JpaRepository<Event, Int> {
    fun findAllByTagsIn(tags: ArrayList<Tag>, pageable: Pageable): List<Event?>
}