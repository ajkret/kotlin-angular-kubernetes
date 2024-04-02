import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {LOCAL_STORAGE_AUTH_DATA_KEY} from "./auth.service";
import {LoggedUser} from "../models/logger-user.model";

export class AuthInterceptorService implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const loggedUser = LoggedUser.fromJson(localStorage.getItem(LOCAL_STORAGE_AUTH_DATA_KEY));
    console.log(`------ LoggedUser ${Date.now()}`)

    // Add JWT token
    if(loggedUser) {
      const newRequest = req.clone({headers: req.headers.append('Authorization', `Bearer ${loggedUser.token}`)})

      return next.handle(newRequest);
    }
    return next.handle(req);
  }
}
