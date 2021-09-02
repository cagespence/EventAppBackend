package com.example.assignment4.entity

import lombok.Data
import lombok.EqualsAndHashCode
import javax.persistence.*
import java.io.Serializable

@Data
@EqualsAndHashCode
@Entity
class Interest: Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int = 0

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private var user: User? = null

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private var tag: Tag? = null

    fun setTag(tag: Tag) {
        this.tag = tag
    }

    fun getTag(): Tag? {
        return tag
    }

    fun setUser(user: User) {
        this.user = user
    }

    override fun equals(other: Any?): Boolean {
        other as Interest
        return (other.tag?.getId() == tag?.getId() && other.user?.getName() == user?.getName())
    }

    override fun toString(): String {
        return "t: " + tag?.getName() + " u: " + user?.getName() + " i: " + tag?.getId()
    }
}