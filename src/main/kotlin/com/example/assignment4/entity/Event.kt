package com.example.assignment4.entity

import lombok.Data
import lombok.EqualsAndHashCode
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*
import javax.persistence.FetchType

@Data
@EqualsAndHashCode
@Entity
class Event : Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int = 0

    @Column(name = "title", nullable = false)
    private var title: String = ""

    @Column(name = "description", nullable = false)
    private var description: String = ""

    @Column(name = "image_url", nullable = false)
    private var imageUrl: String = ""

    @Column(name = "address", nullable = false)
    private var address: String = ""

    @Column(name = "date", nullable = false)
    private var date: LocalDate? = LocalDate.now()

    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false, updatable = false)
    private var host: User? = null

    @Column(name = "startTime", nullable = false)
    private var startTime: String = ""

    @Column(name = "endTime", nullable = false)
    private var endTime:  String = ""

    @Column(name = "createdAt", nullable = false)
    private var createdAt: LocalDateTime =  LocalDateTime.now()

    @Column(name = "updatedAt", nullable = false)
    private var updatedAt: LocalDateTime =  LocalDateTime.now()

    @Column(name = "deletedAt", nullable = true)
    private var deletedAt: LocalDateTime? =  null

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "events")
    private val tags: Set<Tag>? = null

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "event")
    private lateinit var favorites: Set<Favorite>

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "event")
    private lateinit var comments: Set<Comment>

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "event")
    private lateinit var attendees: Set<Attendee>

    // Getters & Setters

    fun setTitle(title: String) {
        this.title = title
    }

    fun setHost(host: User) {
        this.host = host
    }

    fun getHost(): User? {
        return this.host
    }

    fun getFavorites(): Set<Favorite> {
        return this.favorites
    }

    fun getId(): Int {
        return this.id
    }

    fun setDescription(description: String) {
        this.description = description;
    }

    // Date string in format of YYYY-MM-DD
    fun setDate(date: String) {
        val formattedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
        this.date = formattedDate
    }

    fun setStartTime(time: String) {
        this.startTime = time
    }

    fun setEndTime(time: String) {
        this.endTime = time
    }

    fun setDeletedAt(date: LocalDateTime) {
        this.deletedAt = date
    }

    fun setAddress(address: String) {
        this.address = address
    }

    fun setImageUrl(imageUrl: String) {
        this.imageUrl = imageUrl
    }

    fun getAttendees(): Set<Attendee> {
        return this.attendees
    }

    fun getDeletedAt(): LocalDateTime? {
        return this.deletedAt
    }

    fun getTags(): Set<Tag>? {
        return this.tags
    }
}