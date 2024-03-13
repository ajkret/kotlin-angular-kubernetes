package com.dersommer.todolist.services

import com.dersommer.todolist.entities.Task
import com.dersommer.todolist.exceptions.ApplicationException
import com.dersommer.todolist.repositories.TaskRepository
import com.dersommer.todolist.repositories.UserRepository
import com.dersommer.todolist.types.TaskData
import org.springframework.stereotype.Service

// TODO: when implementing TODOs by user, include userId
/**
 * Business rules for recovering Tasks
 */
@Service
class TodoService(val repository: TaskRepository, val userRepository: UserRepository) {
    fun retrieveTasks(userId: Int, includeCompleted: Boolean): List<Task> {
        return if (includeCompleted) {
            repository.findByUserId(userId)
        } else {
            repository.findByUserIdAndCompletedIsFalse(userId)
        }
    }

    fun newTask(data: TaskData): Int {
        val user = userRepository.findById(data.userId ?: 0)
        if (user.isEmpty || data.task == null) throw ApplicationException("Invalid data")
        val task = Task(null, data.task, data.dueTo, data.completed ?: false, user.get())

        val newTask = repository.save(task)
        return newTask.id ?: 0
    }
}