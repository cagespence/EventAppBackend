package com.example.assignment4.service

import com.example.assignment4.entity.Tag
import com.example.assignment4.entity.User
import com.example.assignment4.repository.TagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TagService constructor() {

    @Autowired
    private var tagRepository: TagRepository? = null;

    @Transactional
    fun getTag(id: Int): Tag? {
        return this.tagRepository!!.findById(id).orElse(null);
    }

    @Transactional
    fun getTags():  MutableList<Tag> {
        return this.tagRepository!!.findAll();
    }

//    @Transactional
//    public fun addUserTag(user_id: Int, tag_id: Int): Tag {
//        return null;
//    }

}