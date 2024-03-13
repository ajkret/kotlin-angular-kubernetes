package com.dersommer.todolist.controllers

import com.dersommer.todolist.services.TodoService
import com.dersommer.todolist.services.UserService
import com.dersommer.todolist.types.TaskData
import com.dersommer.todolist.types.UserData
import org.apache.catalina.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors


@RestController
@RequestMapping("/user")
class UserController(val service: UserService) {

    @GetMapping
    fun listAllUserTasks(): List<UserData> {
        return service
            .listAllUsers()
            .stream()
            .map { item -> UserData(item.id, item?.username ?: "") }
            .collect(Collectors.toList());
    }

    @PostMapping
    fun createTask(@RequestBody @Validated user: UserData): Int {
        return service.createUser(user).id ?: 0
    }
}