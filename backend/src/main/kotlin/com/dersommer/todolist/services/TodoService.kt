package com.dersommer.todolist.services

import com.dersommer.todolist.repositories.TaskRepository
import com.dersommer.todolist.types.Task
import org.springframework.stereotype.Service

// TODO: when implementing TODOs by user, include userId
/**
 * Business rules for recovering Tasks
 */
@Service
class TodoService (val repository: TaskRepository) {
    public fun retrieveTasks(userId:Int, includeCompleted:Boolean):List<Task> {
        if(includeCompleted) {
            return repository.findByUserId(userId)
        } else {
            return repository.findByUserIdAndCompletedIsFalse(userId)
        }
    }
}