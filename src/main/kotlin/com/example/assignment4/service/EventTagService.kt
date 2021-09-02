package com.example.assignment4.service

import com.example.assignment4.entity.Event
import com.example.assignment4.entity.EventTag
import com.example.assignment4.entity.Tag
import com.example.assignment4.repository.EventTagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EventTagService constructor() {

    @Autowired
    private var eventTagRepository: EventTagRepository? = null

    @Transactional
    fun addTagToEvent(event: Event, tag: Tag) {
        val eventTag = EventTag()
        eventTag.setEvent(event)
        eventTag.setTag(tag)
        this.eventTagRepository!!.save(eventTag)
    }

    @Transactional
    fun getEventTagByEventAndTag(event: Event, tag: Tag): EventTag? {
        return this.eventTagRepository!!.findByEventAndTag(event, tag);
    }

    @Transactional
    fun deleteEventTag(tag: Tag) {
        this.eventTagRepository!!.deleteByTag(tag);
    }
}