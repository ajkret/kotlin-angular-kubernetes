package com.dersommer.todolist.repositories

import com.dersommer.todolist.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Int> {
    fun findByUsername(username: String): Optional<User>;
}