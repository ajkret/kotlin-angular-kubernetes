package com.dersommer.todolist.types

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(indexes = [
    Index(name="idx_user_username", unique = true, columnList = "username")]
)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Int,

    val username:String
) {
}