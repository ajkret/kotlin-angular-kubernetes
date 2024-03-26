import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from "@angular/common/http"
import { Injectable } from "@angular/core"
import { Observable, Subject, catchError, tap, throwError } from "rxjs"
import { AuthenticationResponse, User } from "./types"
import * as jwt_decode from "jwt-decode"

@Injectable({ providedIn: 'root' })
export class AuthService {
  user = new Subject<User>();

  constructor(private http: HttpClient) { }

  // Error Handling
  private processError(errorResponse: HttpErrorResponse) {
    console.log(errorResponse)
    switch (errorResponse.status) {
      case 401:
        return throwError(() => new Error('Unauthorized'))
      case 400:
        return throwError(() => new Error(errorResponse.error.error_description))
    }
    return throwError(() => new Error('System error'));
  }

  processLogin(data: AuthenticationResponse): void {
    const tokenInfo = jwt_decode.jwtDecode(data.access_token)
    console.log(tokenInfo)

    const user = new User(tokenInfo['preferred_username'], data.access_token, new Date(tokenInfo.exp*1000))
    console.log(user)
    this.user.next(user)
  }

  // Sign Up process - called from Form submit
  signup(username: string, password: string): Observable<AuthenticationResponse> {

    const body = new HttpParams()
      .set('username', username)
      .set('password', password)
      .set('client_id', 'todo')
      .set('grant_type', 'password');

    return this.http.post<AuthenticationResponse>('http://traefik.auth.localdev.me/realms/myrealm/protocol/openid-connect/token',
      body.toString(),
      {
        headers: new HttpHeaders()
          .set('Content-Type', 'application/x-www-form-urlencoded')
      })
      .pipe(catchError(errorResponse => this.processError(errorResponse)),
      tap(data => this.processLogin(data)));
  }
}