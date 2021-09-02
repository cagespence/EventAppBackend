package com.example.assignment4.service

import com.example.assignment4.entity.*
import com.example.assignment4.errors.CustomException
import com.example.assignment4.model.Tags
import com.example.assignment4.repository.EventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import java.time.LocalDateTime


@Service
class EventService constructor() {

    @Autowired
    private val eventRepository: EventRepository? = null

    @Autowired
    private val userService: UserService? = null;

    @Autowired
    private val tagService: TagService? = null;

    @Autowired
    private val eventTagService: EventTagService? = null;

    @Autowired
    private val attendeeService: AttendeeService? = null;


    @Transactional
    public fun createEvent(title: String, description: String, date: String, host: Int, startTime: String, endTime: String, address: String, imageUrl: String, tags: Tags): Event {
        val newEvent = Event()
        newEvent.setTitle(title)
        newEvent.setDescription(description)
        newEvent.setDate(date)
        newEvent.setStartTime(startTime)
        newEvent.setEndTime(endTime)
        newEvent.setAddress(address)
        newEvent.setImageUrl(imageUrl)

        val retrievedHost: User? = (userService!!.getUser(host))

        if (retrievedHost != null) {
            newEvent.setHost(retrievedHost)
        } else {
            throw CustomException("User with ID $host does not exist")
        }

        val savedEvent = this.eventRepository!!.save(newEvent);

        // Add host as attendee of the event.
        this.attendeeService!!.addAttendee(retrievedHost.getId(), savedEvent.getId())

        tags.tagIds.forEach {
            val currentTag = it;
            val retrievedTag: Tag? = (tagService!!.getTag(currentTag))

            if (retrievedTag != null) {
                eventTagService!!.addTagToEvent(savedEvent, retrievedTag)


            } else {
                // Almost an unnecessary safety net, but better safe than sorry.
                this.eventRepository!!.delete(newEvent)   // Delete the event user just made as one of the tags was not found
                this.attendeeService.removeAttendee(retrievedHost.getId(), savedEvent.getId())
                throw CustomException("User with ID $host does not exist")
            }
        }

        return savedEvent
    }

    // title, description, date, startTime, endTime, address, imageUrl, tags
    @Transactional
    public fun updateEvent(eventId: Int, title: String, description: String, date: String, startTime: String, endTime: String, address: String, imageUrl: String, tags: Tags): Event {
        val retrievedEvent: Event? = this.eventRepository!!.findById(eventId).orElse(null)
        val newEventTags: ArrayList<EventTag> = ArrayList()
        val currentEventTags: Set<Tag>? = retrievedEvent!!.getTags()

        val selectedTags: ArrayList<Tag> = ArrayList()


        retrievedEvent.setTitle(title)
        retrievedEvent.setDescription(description)
        retrievedEvent.setDate(date)
        retrievedEvent.setStartTime(startTime)
        retrievedEvent.setEndTime(endTime)
        retrievedEvent.setImageUrl(imageUrl)
        retrievedEvent.setAddress(address)

        if (retrievedEvent != null) {

            // if an empty array of tags is passed (no tags selected, remove all interests from user
            if (tags.tagIds.size == 0) {
                if (currentEventTags != null) {
                    for (eventTag in currentEventTags) {
                        this.eventTagService!!.deleteEventTag(eventTag)
                    }
                }
            }

            tags.tagIds.forEach {
                val selectedTagId = it
                val selectedTag: Tag? = (tagService!!.getTag(selectedTagId))
                val eventTag = EventTag()

                if (selectedTag != null) {
                    selectedTags.add(selectedTag)

                    if (this.eventTagService!!.getEventTagByEventAndTag(retrievedEvent, selectedTag) == null) {
                        eventTag.setTag(selectedTag)
                        eventTag.setEvent(retrievedEvent)
                        newEventTags.add(eventTag)
                    }

                } else {
                    throw CustomException("Tag with ID $selectedTagId does not exist")
                }
            }
        } else {
            throw CustomException("Event with ID $eventId does not exist")
        }

        // delete eventTag from event if the user deselected a tag
        if (currentEventTags != null) {
            for (tag in currentEventTags) {
                val newTag: Tag? = tag
                if (!selectedTags.contains(newTag)) {
                    println("eventTag was removed $tag")
                    this.eventTagService!!.deleteEventTag(tag)
                }
            }
        }

        // add tags to event
        for (eventTag in newEventTags) {
            this.eventTagService!!.addTagToEvent(retrievedEvent, eventTag.getTag()!!)
        }

        return this.eventRepository.save(retrievedEvent);
    }

    @Transactional
    public fun getEvent(id: Int): Event? {
        val event: Event = this.eventRepository!!.findById(id).orElse(null);
        if (event.getDeletedAt() == null) {
            return event
        } else {
            return null
        }
    }

    @Transactional
    public fun removeEvent(userId: Int, eventId: Int): Boolean {
        val eventToRemove = this.eventRepository!!.findById(eventId).orElse(null);

        if (eventToRemove?.getDeletedAt() == null) {
            if (eventToRemove.getHost()!!.getId() == userId) {
                eventToRemove.setDeletedAt(LocalDateTime.now())
                return true
            } else {
                throw CustomException("You are not the owner of this event to remove it!");
            }
        } else {
            throw CustomException("Event with ID $id does not exist!")
        }
    }


    @Transactional
    fun getEvents(tagIds: List<Int>, paginationPage: Int, paginationAmount: Int): List<Event?> {
        val tags = ArrayList<Tag>()

        tagIds.forEach {
            val tagId = it;
            val retrievedTag = tagService!!.getTag(tagId)

            if (retrievedTag != null) {
                tags.add(retrievedTag)
            }
        }

        val paginationInfo = PageRequest.of(paginationPage, paginationAmount, Sort.by("createdAt").descending())

        val events = this.eventRepository!!.findAllByTagsIn(tags, paginationInfo).distinct()

        // Remove any events that have deletedAt set, meaning they have been removed by their host.
        val activeEvents: ArrayList<Event?> = ArrayList()

        for (active in events) {
            if (active?.getDeletedAt() == null) {
                activeEvents.add(active)
            }
        }

        return activeEvents
    }

    @Transactional
    public fun findEventById(id: Int): Event? {
        return this.eventRepository!!.findById(id).orElse(null);
    }
}