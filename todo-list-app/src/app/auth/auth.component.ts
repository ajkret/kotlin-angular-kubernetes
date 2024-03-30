import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent {
  isLoading = false;
  error:string = null;

  constructor(private authService: AuthService) { }

  onSubmit(authForm: NgForm) {
    if (!authForm.valid) {
      return;
    }
    const user = authForm.value.user;
    const password = authForm.value.password;

    this.isLoading = true

    // Call external sign up
    this.authService.signIn(user, password).subscribe({
      next: data => {
        console.log(data)
        this.isLoading = false
        this.error=undefined
      },
      error: error => {
        console.log(error)
        this.error = error.message
        this.isLoading = false
      }
    });

    console.log(authForm.value)
    authForm.reset()
  }
}
