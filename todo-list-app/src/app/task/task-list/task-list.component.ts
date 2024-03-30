import { Component } from '@angular/core';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.css'
})
export class TaskListComponent {
  newTask:string
  newDueTo:Date

  addTodo() {
    
  }

  toggleCompleted(i: number) {
    
  }
}
