package com.dersommer.todolist.controllers

import com.dersommer.todolist.entities.Task
import com.dersommer.todolist.services.TodoService
import com.dersommer.todolist.types.TaskData
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors


@RestController
@RequestMapping("/todo")
class TaskController(val service: TodoService) {

    @GetMapping
    fun listAllUserTasks(
        @RequestParam("username") username: String,
        @RequestParam("completed") includeCompleted: Boolean = false
    ): List<TaskData> {
        return service.retrieveTasks(username, includeCompleted)
            .stream()
            .map { item -> TaskData(item.id, item.task, item.dueTo, item.completed, item.user?.id ?: 0) }
            .collect(Collectors.toList())
    }

    @PostMapping
    fun createTask(@RequestBody @Validated task: TaskData): Int {
        return service.newTask(task)
    }
}