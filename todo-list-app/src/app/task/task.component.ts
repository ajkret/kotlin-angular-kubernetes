import {Component, ViewChild} from '@angular/core';
import {TaskService} from "./task.service";
import {Router} from "@angular/router";
import {TaskListComponent} from "./task-list/task-list.component";

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrl: './task.component.css'
})
export class TaskComponent {
  newTask: string
  newDueTo: string
  isUpdating: boolean = false
  errorMessage: string;
  @ViewChild('taskListComponent') taskListComponent: TaskListComponent;

  constructor(private taskService: TaskService, private router: Router) {}

  addTodo() {
    this.errorMessage = undefined as string
    this.taskService.addNewTask(this.newTask, this.newDueTo).subscribe({
      next: result => this.taskListComponent.refreshTasks(),
      error: error => this.errorMessage = error
    })
  }
}
