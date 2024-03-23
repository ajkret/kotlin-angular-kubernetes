import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, Subject, catchError, throwError } from "rxjs";
import { AuthenticationResponse, User } from "./types";

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

  // Sign Up process - called from Form submit
  signup(username: string, password: string): Observable<any> {

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
      .pipe(catchError(errorResponse => this.processError(errorResponse)));
  }
}