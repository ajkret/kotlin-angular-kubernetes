import {Injectable, OnDestroy, OnInit} from "@angular/core";
import {AuthService} from "../auth/auth.service";
import {Subscription, catchError, tap, throwError} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {LoggedUser} from "../models/logger-user.model";
import {User} from "../models/user.model";
import {environment} from "../../environments/environment";

@Injectable({providedIn: 'root'})
export class UserService {
  userId = 0

  constructor(private http: HttpClient) {}

  updateUser(loggedUser: LoggedUser) {
    const body = new User(loggedUser.id, loggedUser.decodedToken['name'], loggedUser.decodedToken['email']);
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    // Fixed addresses aren't the ideal. Consider also mock data for development mode
    return this.http.post<number>(`${environment.apiUrl}/user`, body, httpOptions)
      .subscribe({
        next: data => this.userId = data,
        error:_errorResponse => throwError(() => new Error('Could not update User'))
      })
  }
}
