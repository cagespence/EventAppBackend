package com.example.assignment4.service

import com.example.assignment4.entity.Comment
import com.example.assignment4.entity.Event
import com.example.assignment4.entity.User
import com.example.assignment4.errors.CustomException
import com.example.assignment4.repository.CommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService constructor() {

    @Autowired
    private var commentRepository: CommentRepository? = null

    @Autowired
    private val userService: UserService? = null;

    @Autowired
    private val eventService: EventService? = null;


    @Transactional
    public fun getComment(id: Int): Comment? {
        return this.commentRepository!!.findById(id).orElse(null);
    }

    @Transactional
    fun createComment(userId: Int, eventId: Int, comment: String): Comment? {
        val newComment = Comment()
        newComment.setContent(comment)

        val retrievedHost: User? = (userService!!.getUser(userId))
        val retrievedEvent: Event? = (eventService!!.getEvent(eventId));

        if (retrievedEvent != null && retrievedHost != null) {
            newComment.setAuthor(retrievedHost)
            newComment.setEvent(retrievedEvent)
        } else {
            throw CustomException("User or event do not exist!")
        }

        return this.commentRepository!!.save(newComment)
    }

}