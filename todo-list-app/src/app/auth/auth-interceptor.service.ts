import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable, Subscription} from "rxjs";
import {AuthService, LOCAL_STORAGE_AUTH_DATA_KEY} from "./auth.service";
import {LoggedUser} from "../models/logger-user.model";

export class AuthInterceptorService implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const loggedUser = JSON.parse(localStorage.getItem(LOCAL_STORAGE_AUTH_DATA_KEY)) as LoggedUser;
    console.log(`------ LoggedUser ${Date.now()}`)
    console.log(loggedUser._token)

    // Add JWT token
    const newRequest = req.clone({ headers: req.headers.append('Authorization',`Bearer ${loggedUser._token}`)})

    return next.handle(newRequest);
  }
}
