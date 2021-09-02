package com.example.assignment4.service

import com.example.assignment4.entity.Attendee
import com.example.assignment4.entity.Event
import com.example.assignment4.entity.User
import com.example.assignment4.repository.AttendeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.example.assignment4.errors.CustomException
import com.example.assignment4.repository.CommentRepository

@Service
class AttendeeService constructor() {

    @Autowired
    private var attendeeRepository: AttendeeRepository?= null

    @Autowired
    private val userService: UserService? = null;

    @Autowired
    private val eventService: EventService? = null;

    @Transactional
    fun addAttendee(user: Int, event: Int): Attendee {
        val newAttendee = Attendee();
        val retrievedUser: User? = (userService!!.getUser(user));
        val retrievedEvent: Event? = (eventService!!.getEvent(event));

        if (retrievedUser != null && retrievedEvent != null) {

            val possibleAttendee = attendeeRepository!!.findByUserAndEvent(retrievedUser, retrievedEvent);

            if (possibleAttendee != null) {
                throw CustomException("User is already an attendee of event.")
            } else {
                newAttendee.setUser(retrievedUser);
                newAttendee.setEvent(retrievedEvent);
            }
        } else {
            throw CustomException("User or event not found.")
        }

        return this.attendeeRepository!!.save(newAttendee);
    }

    @Transactional
    fun removeAttendee(userId: Int, eventId: Int): Boolean {
        val user = this.userService!!.findUserById(userId);
        val event = this.eventService!!.findEventById(eventId);

        if (user != null && event != null) {
            val foundAttendee = this.attendeeRepository!!.findByUserAndEvent(user, event)

            if (foundAttendee != null) {
                this.attendeeRepository!!.deleteById(foundAttendee.getId())
                val deletedAttendee = this.getAttendee(foundAttendee.getId())
                return deletedAttendee == null
            } else {
                throw CustomException("Attendee not found.")
            }
        } else {
            throw CustomException("User or event not found.")
        }
    }

    @Transactional
    fun getAttendee(id: Int): Attendee? {
        return this.attendeeRepository!!.findById(id).orElse(null)
    }
}
