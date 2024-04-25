import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {filter, map, Observable, Subscription} from "rxjs";
import {log} from "@angular-devkit/build-angular/src/builders/ssr-dev-server";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit, OnDestroy {
  showHeader = true
  private navigationSubscription: Subscription;

  constructor(private authService: AuthService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnDestroy(): void {
    this.navigationSubscription.unsubscribe()
  }

  ngOnInit() {
    this.navigationSubscription = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map(() => this.route),
      map(route => {
        while (route.firstChild) route = route.firstChild;
        return route;
      }),
      filter(route => route.outlet === 'primary')
    ).subscribe(route => {
      // Here you can check the current route and decide whether to show the header
      // For example, hide the header on the '/login' route
      this.showHeader = route.snapshot.url.toString() !== 'auth';
    });
  }

  logout() {
    this.authService.signout()
    this.router.navigate(['/auth'])
  }

  isAuthenticated() {
    return this.authService.isAuthenticated();
  }
}
