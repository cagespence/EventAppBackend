package com.example.assignment4.service

import com.example.assignment4.entity.Interest
import com.example.assignment4.entity.Tag
import com.example.assignment4.entity.User
import com.example.assignment4.errors.CustomException
import com.example.assignment4.repository.InterestRepository
import org.springframework.beans.factory.annotation.Autowired
import com.example.assignment4.model.Tags
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.collections.ArrayList


@Service
class InterestService constructor() {

    @Autowired
    private var interestRepository: InterestRepository? = null

    @Autowired
    private val userService: UserService? = null;

    @Autowired
    private val tagService: TagService? = null;

    @Transactional
    fun addInterest(userId: Int, tagIds: Tags): List<Interest?> {

        val retrievedUser: User? = userService?.getUser(userId)
        val newInterests: ArrayList<Interest> = ArrayList()
        val currentInterests: Set<Interest> = retrievedUser!!.getInterests()

        var selectedTags: ArrayList<Tag> = ArrayList()

        println("current interests $currentInterests")
        println("selected tags: " + tagIds.tagIds)

        if (retrievedUser != null) {

            // if an empty array of tags is passed (no tags selected, remove all interests from user
            if (tagIds.tagIds.size == 0){
                println("no tags selected")
                for (interest in currentInterests){
                    this.interestRepository!!.delete(interest)
                }
            }

            tagIds.tagIds.forEach {
                val selectedTagId = it
                val selectedTag: Tag? = (tagService!!.getTag(selectedTagId))
                val interest = Interest()

                if (selectedTag != null) {
                    selectedTags.add(selectedTag)

                    if (this.getInterestByUserAndTag(retrievedUser, selectedTag) == null) {
                        interest.setTag(selectedTag)
                        interest.setUser(retrievedUser)
                        newInterests.add(interest)
                    }

                    // if interest in currentInterests is not found in newInterests, delete that interest

                } else {
                    throw CustomException("Tag with ID $selectedTagId does not exist")
                }
            }

        } else {
            throw CustomException("User with ID $userId does not exist")
        }

        // delete interest from user if the user deselected a tag
        for (interest in currentInterests){
            val newTag: Tag? = interest.getTag()
            if (!selectedTags.contains(newTag)){
                println("interest was removed $interest")
                this.interestRepository!!.delete(interest)
            }
        }

        // add interests to user
        for(interest in newInterests){
            println("interest was added $interest")
            this.interestRepository!!.save(interest)
        }

        return newInterests
    }

    @Transactional
    public fun removeInterest(like: Int) {
        return this.interestRepository!!.deleteById(like)
    }

    @Transactional
    public fun getInterest(id: Int): Interest? {
        return this.interestRepository!!.findById(id).orElse(null)
    }

    @Transactional
    fun getInterestByUserAndTag(user: User, tag: Tag): Interest? {
        return this.interestRepository!!.findByUserAndTag(user, tag)
    }


}