package com.dersommer.todolist.entities

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
class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "task")
    var task: String

    @Column(name = "due_to")
    var dueTo: LocalDate? = null

    @Column(name = "completed")
    var completed: Boolean = false

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // This is the foreign key column in the Task table
    var user: User? = null

    constructor() : this(task = "")

    constructor(
        id: Int? = null,
        task: String,
        dueTo: LocalDate? = null,
        completed: Boolean = false,
        user: User? = null
    ) {
        this.id = id
        this.task = task
        this.dueTo = dueTo
        this.completed = completed
        this.user = user
    }
}