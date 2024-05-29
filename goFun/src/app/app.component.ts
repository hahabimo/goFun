import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MemberListComponent } from './member-list/member-list.component';
import { LoginComponent } from './login/login.component';
import { TodoListComponent } from './todo-list/todo-list.component';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [CommonModule, RouterModule, LoginComponent, MemberListComponent
    ,TodoListComponent]
})
export class AppComponent {
  title = 'GoFun';
  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {}

  isLoggedIn(): boolean {
    return this.authService.getToken() !== null;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  navigateToRegister() {
    this.router.navigate(['/register']);
  }
}