import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './auth/auth.component';
import { AppComponent } from './app.component';
import {CreateAuthGuard} from "./auth/auth.guard";
import {PageNotFoundComponent} from "./shared/page-not-found/page-not-found.component";
import {TodoComponent} from "./todo/todo.component";

const routes: Routes = [
  { path: '', redirectTo: '/todo', pathMatch: 'full' },
  { path: 'auth', component: AuthComponent },
  { path: 'todo', component: TodoComponent, canActivate: [CreateAuthGuard()] },
  { path: 'not-found', component: PageNotFoundComponent },
  { path: '**', redirectTo: '/not-found' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
