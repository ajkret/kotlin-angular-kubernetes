package com.dersommer.todolist.repositories

import com.dersommer.todolist.entities.Task
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface TaskRepository : JpaRepository<Task, Int> {
    fun findByDueToAfter(date: LocalDate): List<Task>
    fun findByUserId(userId: Int): List<Task>
    fun findByUserIdAndCompletedIsFalse(userId: Int): List<Task>

}
