import {Component, OnDestroy, OnInit} from '@angular/core';
import {TaskService} from "../task.service";
import {Task} from "../../models/task.model";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.css'
})
export class TaskListComponent implements OnInit {
  tasks: Task[] = [];

  constructor(private authService: AuthService, private taskService: TaskService) {
  }

  ngOnInit() {
    this.loadTasks()
  }

  loadTasks() {
    this.taskService.refreshTaskListForUser().subscribe({
      next: (tasks) => {
        this.tasks = tasks;
      },
      error: (err) => console.error('Failed to get tasks', err)
    });
  }

  refreshTasks() {
    this.loadTasks();  // Reuse the loadTasks method to refresh the task list
  }

  toggleCompleted(i: number) {


  }
}
