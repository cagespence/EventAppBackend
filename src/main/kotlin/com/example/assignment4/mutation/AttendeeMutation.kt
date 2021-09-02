package com.example.assignment4.mutation

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.example.assignment4.entity.Attendee
import com.example.assignment4.security.Security
import com.example.assignment4.service.AttendeeService
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AttendeeMutation : GraphQLMutationResolver {

    @Autowired
    private val attendeeService: AttendeeService? = null

    fun createAttendee(userId: Int, eventId: Int, env: DataFetchingEnvironment): Attendee {
        Security().authenticate(env)
        return this.attendeeService!!.addAttendee(userId, eventId)
    }

    fun removeAttendee(userId: Int, eventId: Int, env: DataFetchingEnvironment): Boolean {
        Security().authenticate(env)
        return this.attendeeService!!.removeAttendee(userId, eventId)
    }
}