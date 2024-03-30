package com.dersommer.todolist.services

import com.dersommer.todolist.entities.User
import com.dersommer.todolist.exceptions.ApplicationException
import com.dersommer.todolist.repositories.UserRepository
import com.dersommer.todolist.types.UserData
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(val repository: UserRepository) {

    /**
     * Creates or updates the User (for simplicity), returning the User with the proper user id
     */
    @Transactional
    fun createOrUpdateUser(data: UserData): User {
        // Lookup user by 'username'
        if (data.username == null) throw ApplicationException("Invalid request")
        val user = repository.findByUsername(data.username)
        if (user.isPresent) {
            return user.get().apply {
                this.name = data.name ?: this.name
                this.email = data.email ?: this.email
            }.let(repository::save)
        }
        return repository.save(User(username = data.username, name = data.name ?: "", email = data.email ?: ""))
    }

    /**
     * Return the list of all users
     */
    fun listAllUsers(): List<User> {
        return repository.findAll();
    }
}