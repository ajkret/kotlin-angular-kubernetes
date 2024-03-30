package com.dersommer.todolist.types

import jakarta.validation.constraints.NotBlank

class UserData(
    val id:Int?=null,

    @field:NotBlank(message = "username must not be blank")
    val username:String?,
    @field:NotBlank(message = "Name must not be blank")
    val name:String?,
    @field:NotBlank(message = "Email must not be blank")
    val email:String?
) {
}