package com.example.assignment4.mutation

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.example.assignment4.entity.Event
import com.example.assignment4.model.Tags
import com.example.assignment4.security.Security
import com.example.assignment4.service.EventService
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component



@Component
class EventMutation : GraphQLMutationResolver {

    @Autowired
    private val eventService: EventService? = null

    fun createEvent(title: String, description: String, address: String, imageUrl: String, date: String, host: Int, startTime: String, endTime: String, tags: Tags, env: DataFetchingEnvironment): Event {
        Security().authenticate(env)
        return this.eventService!!.createEvent(title, description, date, host, startTime, endTime, address, imageUrl, tags)
    }

    fun updateEvent(eventId: Int, title: String, description: String, address: String, imageUrl: String, date: String, startTime: String, endTime: String, tags: Tags, env: DataFetchingEnvironment): Event{
        Security().authenticate(env)
        return this.eventService!!.updateEvent(eventId, title, description, date, startTime, endTime, address, imageUrl, tags)
    }

    fun removeEvent(userId: Int, eventId: Int, env: DataFetchingEnvironment): Boolean {
        Security().authenticate(env)
        return this.eventService!!.removeEvent(userId,eventId);
    }
}