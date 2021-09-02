package com.example.assignment4.repository

import com.example.assignment4.entity.Event
import com.example.assignment4.entity.EventTag
import com.example.assignment4.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventTagRepository  : JpaRepository<EventTag, Int> {
    fun deleteByTag(tag: Tag)
    fun findByEventAndTag(event: Event, tag: Tag): EventTag?
}