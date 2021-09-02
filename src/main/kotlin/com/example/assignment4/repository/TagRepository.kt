package com.example.assignment4.repository

import com.example.assignment4.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<Tag, Int> {
    fun findByIdIn(ids: Array<Int>): Iterable<Tag>
}