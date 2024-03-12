package com.dersommer.todolist.repositories

import com.dersommer.todolist.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {
}