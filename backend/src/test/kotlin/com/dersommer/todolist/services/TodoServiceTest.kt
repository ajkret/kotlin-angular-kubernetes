package com.dersommer.todolist.services

import com.dersommer.todolist.entities.Task
import com.dersommer.todolist.entities.User
import com.dersommer.todolist.exceptions.ApplicationException
import com.dersommer.todolist.repositories.TaskRepository
import com.dersommer.todolist.repositories.UserRepository
import com.dersommer.todolist.types.TaskData
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.util.*
import java.util.stream.Collectors

class TodoServiceTest {

    private lateinit var user: User
    private lateinit var completedTasks: List<Task>
    private lateinit var repo: TaskRepository
    private lateinit var userRepo: UserRepository
    private lateinit var service: TodoService

    @BeforeEach
    fun setUp() {
        user = User(1, "test")
        completedTasks = listOf(
            Task(1, "Test", LocalDate.now().plusDays(2), false, user),
            Task(2, "Test 2", LocalDate.now().plusDays(1), true, user)
        )

        // Initialize mock for the Repository, that will be injected into Service
        repo = mock<TaskRepository>()
        userRepo = mock<UserRepository>()

        // I didn't understand why org.mockito.kotlin.any() didn't inferred the correct class
        whenever(repo.save(any(Task::class.java))).then { param ->
            val data: Task = param.getArgument(0) as Task

            // Magic - new instance of Task will be returned
            Task(1, data.task, data.dueTo, data.completed, user)
        }

        // Mock repository implementation
        whenever(repo.findAll()).thenReturn(completedTasks)
        whenever(repo.findByUserId(anyInt())).then { _ ->
            completedTasks.stream().filter { task -> task.user?.id == user.id }.collect(Collectors.toList())
        }
        whenever(repo.findByUserIdAndCompletedIsFalse(anyInt())).then { _ ->
            completedTasks.stream().filter { task -> !task.completed && task.user?.id == user.id }
                .collect(Collectors.toList())
        }

        service = TodoService(repo, userRepo)
    }

    @Test
    fun `Should return open Tasks`() {
        val result = service.retrieveTasks(user.id ?: 1, false)
        assertNotNull(result)
        assertEquals(1, result.size)
        assertFalse(result[0].completed)
    }

    @Test
    fun `Should return open and completed Tasks`() {
        val result = service.retrieveTasks(user.id ?: 1, true)
        assertNotNull(result)
        assertEquals(2, result.size)
    }

    @Test
    fun `Should create a new Task`() {
        whenever(userRepo.findById(anyInt())).thenReturn(Optional.of(user))

        val result = service.newTask(TaskData(null, "test", LocalDate.now().plusDays(1), false, 10))
        assertEquals(1, result)
    }

    @Test
    fun `Should fail to create a new Task due to user not found`() {
        whenever(userRepo.findById(anyInt())).thenReturn(Optional.empty())

        val exception = assertThrows<ApplicationException> {
            service.newTask(TaskData(null, "test", LocalDate.now().plusDays(1), false, 10))
        }

        assertEquals("Invalid data", exception.message)
    }
}