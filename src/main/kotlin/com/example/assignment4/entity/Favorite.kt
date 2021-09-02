package com.example.assignment4.entity

import lombok.Data
import lombok.EqualsAndHashCode
import javax.persistence.*
import java.io.Serializable

@Data
@EqualsAndHashCode
@Entity
class Favorite : Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int = 0

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private var user: User? = null

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private var event: Event? = null

    // Getters & Setters

    fun setEvent(event: Event) {
        this.event = event
    }

    fun setUser(user: User) {
        this.user = user
    }

    fun getUser(): User? {
        return this.user
    }

    fun getId(): Int {
        return this.id
    }
}