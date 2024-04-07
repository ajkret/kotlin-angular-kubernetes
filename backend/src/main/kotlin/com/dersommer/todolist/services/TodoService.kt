package com.dersommer.todolist.services

import com.dersommer.todolist.entities.Task
import com.dersommer.todolist.exceptions.ApplicationException
import com.dersommer.todolist.repositories.TaskRepository
import com.dersommer.todolist.repositories.UserRepository
import com.dersommer.todolist.types.TaskData
import org.springframework.stereotype.Service
import java.util.*

/**
 * Business rules for recovering Tasks
 */
@Service
class TodoService(val repository: TaskRepository, val userRepository: UserRepository) {
    fun retrieveTasks(username: String, includeCompleted: Boolean): List<Task> {
        // Find user Id by its username
        val user = userRepository.findByUsername(username)

        // Retrieve the list of tasks per user. Ignore invalid users
        return user.map {
            if (includeCompleted) {
                repository.findByUserId(it.id ?: 0)
            } else {
                repository.findByUserIdAndCompletedIsFalse(it.id ?: 0)
            }
        }.orElse(listOf())
    }

    fun newTask(data: TaskData): Int {
        val user = userRepository.findById(data.userId ?: 0)
        if (user.isEmpty || data.task == null) throw ApplicationException("Invalid data")
        val task = Task(null, data.task, data.dueTo, data.completed ?: false, user.get())

        val newTask = repository.save(task)
        return newTask.id ?: 0
    }
}
