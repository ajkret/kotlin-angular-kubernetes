package com.dersommer.todolist.controllers

import com.dersommer.todolist.services.UserService
import com.dersommer.todolist.types.UserData
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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
            .map { item -> UserData(item.id, item?.username ?: "", item.name?:"", item.email?:"") }
            .collect(Collectors.toList());
    }

    @PostMapping
    fun createUser(@RequestBody @Validated user: UserData): Int {
        return service.createOrUpdateUser(user).id ?: 0
    }
}