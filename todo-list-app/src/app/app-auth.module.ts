import { APP_INITIALIZER, NgModule } from "@angular/core";
import { KeycloakAngularModule, KeycloakService } from "keycloak-angular";

function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://traefik.auth.localdev.me/auth', // Keycloak server URL
        realm: 'myRealm', // Name of the realm
        clientId: 'todo', // Client ID
      },
      initOptions: {
        onLoad: 'login-required', // Forces login if not logged in already
        checkLoginIframe: false
      },
    });
}

@NgModule({
  imports: [
    KeycloakAngularModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    }
  ],
})
export class AppAuthModule {

}