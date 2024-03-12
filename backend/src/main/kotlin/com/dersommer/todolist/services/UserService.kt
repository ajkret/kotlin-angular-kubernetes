package com.dersommer.todolist.services

import com.dersommer.todolist.entities.User
import com.dersommer.todolist.repositories.UserRepository
import com.dersommer.todolist.types.UserData
import org.springframework.stereotype.Service

@Service
class UserService(val repository: UserRepository) {

    fun createUser(data: UserData):User {
        return repository.save(User(null, data.username))
    }

    fun listAllUsers(): List<User> {
        return repository.findAll();
    }
}