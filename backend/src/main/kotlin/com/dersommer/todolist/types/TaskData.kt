package com.dersommer.todolist.types

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import java.time.LocalDate

data class TaskData(
    val id: Int? = null,
    @field:NotBlank(message = "Task must not be blank")
    val task: String?,
    val dueTo: LocalDate? = null,
    val completed: Boolean? = false,
    @field:NotEmpty(message = "User must be identified")
    val userId: Int?
)