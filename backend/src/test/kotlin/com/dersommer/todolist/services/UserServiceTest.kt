package com.dersommer.todolist.services

import com.dersommer.todolist.entities.User
import com.dersommer.todolist.repositories.UserRepository
import com.dersommer.todolist.types.UserData
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.Optional

class UserServiceTest {
    private lateinit var repository: UserRepository
    private lateinit var fixture:UserService
    private lateinit var user:User

    @BeforeEach
    fun setUp() {
        repository = mock<UserRepository>()
        fixture =UserService(repository)

        user = User(1, "test", "test", "test@test.com")

        whenever(repository.save(Mockito.any(User::class.java))).then { param ->
            val incoming:User = param.getArgument(0)

            User(if(incoming.id==null) 999 else incoming.id, incoming.username, incoming.name, incoming.email)
        }
    }

    @Test
    fun `Should update User`() {
        whenever(repository.findByUsername(any())).thenReturn(Optional.of(user))

        val result = fixture.createOrUpdateUser(UserData(username="test01", name="Test Test", email="test01@test.com"))

        assertNotNull(result)
        assertEquals(1, result.id)
        assertEquals("Test Test", result.name)
        assertEquals("test01@test.com", result.email)
    }

    @Test
    fun `Should insert new User`() {
        whenever(repository.findByUsername(any())).thenReturn(Optional.empty())

        val result = fixture.createOrUpdateUser(UserData(username="test", name="Test", email="test@test.com"))

        assertNotNull(result)
        assertEquals(999, result.id)
        assertEquals("Test", result.name)
        assertEquals("test@test.com", result.email )
    }
}