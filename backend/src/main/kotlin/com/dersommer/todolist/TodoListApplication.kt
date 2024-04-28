package com.dersommer.todolist

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
class TodoListApplication

fun main(args: Array<String>) {
    runApplication<TodoListApplication>(*args)
}