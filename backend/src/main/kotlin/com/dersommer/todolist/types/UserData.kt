package com.dersommer.todolist.types

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

class UserData(
    val id:Int?,
    val username:String
) {
}