package com.dersommer.todolist.controllers

import com.dersommer.todolist.services.TodoService
import com.dersommer.todolist.types.Task
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/todo")
class TaskController(val service: TodoService) {

    @GetMapping
    fun listAllUserTasks(
        @RequestParam("id")userId:Int,
        @RequestParam("completed")includeCompleted:Boolean=false): List<Task> {
        return service.retrieveTasks(userId, includeCompleted);
    }
}