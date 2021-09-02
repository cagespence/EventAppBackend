package com.example.assignment4.entity

import lombok.Data
import lombok.EqualsAndHashCode
import javax.persistence.*
import java.io.Serializable
import java.time.LocalDateTime

@Data
@EqualsAndHashCode
@Entity
class Comment : Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int = 0

    @Column(name = "content", nullable = false)
    private var content: String = ""

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private var author: User? = null

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private var event: Event? = null

    @Column(name = "createdAt", nullable = false)
    private var createdAt: LocalDateTime =  LocalDateTime.now()

    @Column(name = "deletedAt", nullable = false)
    private var deletedAt: LocalDateTime? = null

    @Column(name = "updatedAt", nullable = false)
    private var updatedAt:  LocalDateTime =  LocalDateTime.now()

    // GETTERS AND SETTERS

    fun setAuthor(author: User) {
        this.author = author
    }

    fun setContent(content: String) {
        this.content = content
    }

    fun setEvent(event: Event) {
        this.event = event
    }

}