package com.example.assignment4.repository

import com.example.assignment4.entity.Interest
import com.example.assignment4.entity.Tag
import com.example.assignment4.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InterestRepository : JpaRepository<Interest, Int> {
    fun findByUserAndTag(user: User, tag: Tag): Interest?

}
