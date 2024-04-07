import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {UserService} from "../user/user.service";
import {LoggedUser} from "../models/logger-user.model";
import {User} from "../models/user.model";
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
}
