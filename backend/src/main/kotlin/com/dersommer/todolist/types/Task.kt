package com.dersommer.todolist.types

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) val id: Int? = null,

    @Column(name = "task")
    val task: String,

    @Column(name = "due_to")
    val dueTo: LocalDate? = null,

    @Column(name = "completed")
    val completed: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // This is the foreign key column in the Task table
    val user: User? = null
)