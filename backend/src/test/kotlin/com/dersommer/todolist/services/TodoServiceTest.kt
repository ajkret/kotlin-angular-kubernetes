package com.dersommer.todolist.services

import com.dersommer.todolist.repositories.TaskRepository
import com.dersommer.todolist.types.Task
import com.dersommer.todolist.types.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.util.stream.Collectors

class TodoServiceTest {

    private lateinit var user: User
    private lateinit var completedTasks: List<Task>
    private lateinit var repo: TaskRepository
    private lateinit var service: TodoService

    @BeforeEach
    fun setUp() {
        user = User(1, "test")
        completedTasks = listOf(
            Task(1, "Test", LocalDate.now().plusDays(2), false, user),
            Task(2, "Test 2", LocalDate.now().plusDays(1), true, user)
        )

        // Initialize mock for the Repository, that will be injected into Service
        repo = mock()

        whenever(repo.findAll()).thenReturn(completedTasks);
        whenever(repo.findByUserId(anyInt())).then { param ->
            completedTasks.stream().filter({ task -> task.user?.id == user?.id }).collect(Collectors.toList())
        }
        whenever(repo.findByUserIdAndCompletedIsFalse(anyInt())).then { param ->
            completedTasks.stream().filter({ task -> !task.completed && task.user?.id == user?.id })
                .collect(Collectors.toList())
        }

        service = TodoService(repo)
    }

    @Test
    fun `Should return open Tasks`() {
        val result = service.retrieveTasks(user.id, false)
        assertNotNull(result)
        assertEquals(1, result.size)
        assertFalse(result[0].completed)
    }

    @Test
    fun `Should return open and completed Tasks`() {
        val result = service.retrieveTasks(user.id, true)
        assertNotNull(result)
        assertEquals(2, result.size)
    }
}