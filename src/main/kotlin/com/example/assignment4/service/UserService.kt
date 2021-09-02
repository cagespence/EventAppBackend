package com.example.assignment4.service

import com.example.assignment4.entity.Event
import com.example.assignment4.entity.User
import com.example.assignment4.model.AuthData
import com.example.assignment4.repository.AttendeeRepository
import com.example.assignment4.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService constructor() {

//    private var userRepository: UserRepository = userRepository;

    @Autowired
    private var userRepository: UserRepository?= null

    @Autowired
    private val eventService: EventService? = null;

    @Autowired
    private val attendeeService: AttendeeService? = null;

    @Transactional
    public fun createUser(name: String, dob: String, bio: String = "", authData: AuthData): User {
        var newUser = User()
        newUser.setName(name)
        newUser.setDob(dob)
        newUser.setBio(bio)
        newUser.setPassword(BCryptPasswordEncoder().encode(authData.password))
        newUser.setEmail(authData.email)
        return this.userRepository!!.save(newUser)
    }

    @Transactional
    public fun getUser(id: Int): User? {
        return this.userRepository!!.findById(id).orElse(null)
    }

    @Transactional
    public fun getUserByEmail(email: String): User? {
        return this.userRepository!!.findByEmail(email)
    }

    @Transactional
    public fun findByEmailAndPassword(email: String, password: String): User? {
        return this.userRepository!!.findByEmailAndPassword(email, password).orElse(null);
    }

    @Transactional
    public fun findUserById(id: Int): User? {
        return this.userRepository!!.findById(id).orElse(null);
    }

}