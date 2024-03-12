package com.dersommer.todolist.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    indexes = [
        Index(name = "idx_user_username", unique = true, columnList = "username")]
)
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    var username: String

    constructor() : this(username = "")

    constructor(id: Int? = null, username: String) {
        this.id = id
        this.username = username
    }
}