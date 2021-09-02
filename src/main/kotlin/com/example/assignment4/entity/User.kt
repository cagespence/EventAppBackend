package com.example.assignment4.entity

import lombok.Data
import lombok.EqualsAndHashCode
import javax.persistence.*
import java.io.Serializable
import java.time.LocalDate


@Data
@EqualsAndHashCode
@Entity
class User : Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int = 0

    @Column(name = "name", nullable = false)
    private var name: String = ""

    @Column(name = "dob", nullable = false)
    private var dob: String = ""

    @Column(name = "bio", nullable = true)
    private var bio: String = ""

    @Column(name = "password", nullable = true)
    private var password: String = ""

    @Column(name = "email", nullable = true)
    private var email: String = ""

    @Column(name = "createdAt", nullable = false)
    private var createdAt: LocalDate = LocalDate.now()

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private lateinit var favorites: Set<Favorite>

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private lateinit var interests: Set<Interest>

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private lateinit var attended: Set<Attendee>

    // Getters & Setters

    fun setName(name: String) {
        this.name = name
    }

    fun setDob(dob: String) {
        this.dob = dob
    }

    fun setBio(bio: String) {
        this.bio = bio
    }

    fun getId(): Int {
        return this.id
    }

    fun getName(): String {
        return this.name
    }

    fun getEmail(): String {
        return this.email
    }

    fun getPassword(): String {
        return this.password
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun setPassword(pass: String) {
        this.password = pass
    }

    fun getInterests():Set<Interest>{
//        println("getting interests from user - " + this.interests.size)
        return this.interests
    }

}