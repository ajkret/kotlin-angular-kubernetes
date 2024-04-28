package com.dersommer.todolist.types

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class TaskData(
    val id: Int? = null,
    @field:NotBlank(message = "Task must not be blank")
    val task: String?,
    val dueTo: LocalDate? = null,
    val completed: Boolean? = false,
    @field:NotNull(message = "User must be identified")
    val userId: Int?
)