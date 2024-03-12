package com.dersommer.todolist.types

import java.time.LocalDate

data class TaskData(
    val id: Int? = null,
    val task: String,
    val dueTo: LocalDate? = null,
    val completed: Boolean = false,
    val userId: Int
)