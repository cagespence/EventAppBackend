package com.example.assignment4.query

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.example.assignment4.entity.Event
import com.example.assignment4.model.EventInfo
import com.example.assignment4.errors.CustomException
import com.example.assignment4.model.Tags
import com.example.assignment4.security.Security
import com.example.assignment4.service.EventService
import graphql.schema.DataFetchingEnvironment
import graphql.servlet.context.GraphQLContext
import graphql.servlet.context.GraphQLServletContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest



@Component
class EventQuery : GraphQLQueryResolver {

    @Autowired
    private val eventService: EventService? = null

    fun getEvent(id: Int, env: DataFetchingEnvironment): EventInfo? {
        Security().authenticate(env)

        val event = this.eventService!!.getEvent(id)
        val email = (env.getContext() as GraphQLServletContext).httpServletRequest.getHeader("UserEmail")
        val additionalEventInfo: UserEventInfo

        if (event != null) {
            additionalEventInfo = getUserDetailsFromEvent(event, email)
        } else {
            throw CustomException("This event with ID $id does not exist")
        }

        return EventInfo(event, isAttending = additionalEventInfo.isAttending(), isLiked = additionalEventInfo.isLiked(), isHost = additionalEventInfo.isHost())
    }

    fun getEvents(filter: Tags, paginationStartAt: Int, paginationEndAt: Int, env: DataFetchingEnvironment): List<EventInfo?> {
        Security().authenticate(env)
        val events = this.eventService!!.getEvents(filter.tagIds, paginationStartAt, paginationEndAt)

        val email = (env.getContext() as GraphQLServletContext).httpServletRequest.getHeader("UserEmail")

        val eventInfoArray: ArrayList<EventInfo> = ArrayList()

        if (events.isNotEmpty()) {
            for (event in events) {
                if (event != null) {
                    val additionalEventInfo: UserEventInfo = getUserDetailsFromEvent(event, email)
                    val eventInfo = EventInfo(event, isAttending = additionalEventInfo.isAttending(), isLiked = additionalEventInfo.isLiked(), isHost = additionalEventInfo.isHost())
                    eventInfoArray.add(eventInfo)
                }
            }
        }

        return eventInfoArray
    }


    interface EventAdditionsForUserInterface {
        fun isAttending(): Boolean
        fun isHost(): Boolean
        fun isLiked(): Boolean
    }

    class UserEventInfo(private val attending: Boolean, private val host: Boolean, private val liked: Boolean) : EventAdditionsForUserInterface {
        override fun isAttending(): Boolean {
            return this.attending
        }

        override fun isHost(): Boolean {
            return this.host
        }

        override fun isLiked(): Boolean {
            return this.liked
        }

    }

    private fun getUserDetailsFromEvent(event: Event, userEmail: String): UserEventInfo {
        var isHost = false
        var isLiked = false
        var isAttending = false

        // Check if the user is in the attendees of the event
        if (event.getAttendees().isNotEmpty()) {
            for (attendee in event.getAttendees()) {
                val user = attendee.getUser()

                // Check if user email matches attendee email
                if (user?.getEmail().equals(userEmail)) {
                    isAttending = true
                }
            }
        }

        // Check if user is also the host of the event
        if (event.getHost()!!.getEmail() == userEmail) {
            isHost = true
        }

        // Check if user has favorited this event
        if (event.getFavorites().isNotEmpty()) {
            for (favorites in event.getFavorites()) {
                val user = favorites.getUser()
                if (user != null && user.getEmail() == userEmail) {
                       isLiked = true
                }
            }
        }

        return UserEventInfo(isAttending, host = isHost, liked = isLiked)
    }
}