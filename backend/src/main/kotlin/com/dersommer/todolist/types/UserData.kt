package com.dersommer.todolist.types

import jakarta.validation.constraints.NotBlank

class UserData(
    val id:Int?,

    @field:NotBlank(message = "Name must not be blank")
    val username:String?
) {
}