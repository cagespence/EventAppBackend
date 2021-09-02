package com.example.assignment4.entity

import lombok.Data
import lombok.EqualsAndHashCode
import java.io.Serializable
import javax.persistence.*
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Data
@EqualsAndHashCode
@Entity
class Tag : Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int = 0

    @Column(name = "name", nullable = false)
    private var name: String = ""

    @ManyToMany(fetch = FetchType.EAGER,cascade = [CascadeType.ALL])
    @JoinTable(name = "event_tag", joinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "ID")], inverseJoinColumns = [JoinColumn(name = "event_id", referencedColumnName = "ID")])
    private val events: Set<Event>? = null

    public fun getId(): Int{
        return this.id
    }

    public fun getName(): String {
        return this.name
    }

    fun setName(name: String) {
        this.name = name;
    }

    override fun toString(): String {
        return "tag: "+  name + ", id: " + id
    }
}


