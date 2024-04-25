import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from "@angular/common/http"
import {Injectable} from "@angular/core"
import {Observable, Subject, catchError, tap, throwError} from "rxjs"
import * as jwt_decode from "jwt-decode"
import {LoggedUser} from "../models/logger-user.model";
import {environment} from "../../environments/environment";
import {Router} from "@angular/router";
import {User} from "../models/user.model";

export const LOCAL_STORAGE_AUTH_DATA_KEY = 'todo-list-app-user';

interface AuthenticationResponse {
  access_token: string,
  expires_in: number,
  refresh_expires_in: number,
  refresh_token: string,
  token_type: string,
  'not-before-policy': number,
  session_state: string,
  scope: string
}

@Injectable({providedIn: 'root'})
export class AuthService {
  authenticatedUser = new Subject<LoggedUser>();
  loggedUser: LoggedUser
  userId: number;

  constructor(private http: HttpClient, private router: Router) {
  }

  // Error Handling from http pipe()
  private handleAuthenticationErrors(errorResponse: HttpErrorResponse) {
    console.log(errorResponse)
    switch (errorResponse.status) {
      case 401:
        return throwError(() => new Error('Unauthorized'))
      case 400:
        return throwError(() => new Error(errorResponse.error.error_description))
    }
    return throwError(() => new Error('System error'));
  }

  // Handle successful authentication - http.pipe()
  private handleAuthentication(data: AuthenticationResponse): void {
    const tokenInfo = jwt_decode.jwtDecode(data.access_token)
    console.log(tokenInfo)

    this.loggedUser = new LoggedUser(tokenInfo['preferred_username'], data.access_token, tokenInfo.exp * 1000, tokenInfo)
    console.log(this.loggedUser)

    // Store data for auto-login
    localStorage.setItem(LOCAL_STORAGE_AUTH_DATA_KEY, JSON.stringify(this.loggedUser));
    this.authenticatedUser.next(this.loggedUser)

    // Update the user - make sure this will be called after localStorage and Observable.next are called
    this.updateUser(this.loggedUser)

    this.router.navigate(['/todo'])
  }

  // Called from AppComponent OnInit() to load user from localStorage
  autoSignin() {
    const authData = localStorage.getItem(LOCAL_STORAGE_AUTH_DATA_KEY)
    if (authData) {
      const user = LoggedUser.fromJson(authData);
      if (user.token) {
        this.loggedUser = user
        this.authenticatedUser.next(user)
        return
      }
    }
    this.router.navigate(['/auth'])
  }

  // Sign In process - called from Form submit
  signIn(username: string, password: string): Observable<AuthenticationResponse> {
    const body = new HttpParams()
      .set('username', username)
      .set('password', password)
      .set('client_id', 'todo')
      .set('grant_type', 'password');

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded'
      })
    }

    // Fixed addresses aren't the ideal. Consider also mock data for development mode
    return this.http.post<AuthenticationResponse>(environment.authenticationUrl, body.toString(), httpOptions)
      .pipe(
        catchError(errorResponse => this.handleAuthenticationErrors(errorResponse)),
        tap(data => this.handleAuthentication(data))
      )
  }

  signout() {
    this.authenticatedUser.next(null)
    localStorage.removeItem(LOCAL_STORAGE_AUTH_DATA_KEY)
  }

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
        error: _errorResponse => throwError(() => new Error('Could not update User'))
      })
  }

  isAuthenticated() {
    return this.loggedUser && this.loggedUser.token;
  }
}
