package com.example.assignment4.entity

import lombok.Data
import lombok.EqualsAndHashCode
import java.io.Serializable
import javax.persistence.*

@Data
@EqualsAndHashCode
@Entity
class EventTag: Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int = 0

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private var event: Event? = null

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private var tag: Tag? = null

    fun setTag(tag: Tag) {
        this.tag = tag
    }

    fun setEvent(event: Event) {
        this.event = event
    }

    fun getTag(): Tag? {
        return this.tag
    }

}