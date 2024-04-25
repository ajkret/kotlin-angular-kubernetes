import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree} from '@angular/router'
import {inject} from '@angular/core'
import {AuthService, LOCAL_STORAGE_AUTH_DATA_KEY} from "./auth.service";
import {map, Observable} from 'rxjs'
import {LoggedUser} from "../models/logger-user.model";

export function CreateAuthGuard(): CanActivateFn {

  // Creates the Guard
  return (route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> => {
    const router = inject(Router)

    const authData = localStorage.getItem(LOCAL_STORAGE_AUTH_DATA_KEY)
    if (authData) {
      const loggedUser = LoggedUser.fromJson(authData);

      const isAuthenticated = loggedUser && loggedUser.token
      if (isAuthenticated) {
        console.log('Authenticated')
        return true;
      }
    }
    router.navigate(['/auth'])
    return false;
  }
}
