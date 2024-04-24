import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {map, Observable, throwError} from "rxjs";
import {Task} from "../models/task.model";
import {AuthService} from "../auth/auth.service";

@Injectable({providedIn: 'root'})
export class TaskService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  refreshTaskListForUser(): Observable<Task[]> {
    const options = {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json'),
      params: new HttpParams()
        .set('username', this.authService.loggedUser.id)
        .set('completed', 'false')
    }

    return this.http.get<Task[]>(`${environment.apiUrl}/todo`, options)
      .pipe(
        map(tasks => tasks.map(task => ({
          ...task,
          dueTo: new Date(task.dueTo)  // Convert the date string to a Date object
        })))
      )
  }

  addNewTask(newTask: string, dueTo: string) {
    const options = {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
    }

    const body = {
      task: newTask,
      dueTo: new Date(dueTo).toISOString(),
      completed: "false",
      userId: this.authService.userId
    }
    return this.http.post<number>(`${environment.apiUrl}/todo`, body, options)
  }
}
