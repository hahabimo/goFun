import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MemberListComponent } from './member-list/member-list.component';
import { LoginComponent } from './login/login.component';
import { TodoListComponent } from './todo-list/todo-list.component';
import { Router } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [CommonModule, RouterModule, LoginComponent, MemberListComponent
    ,TodoListComponent]
})
export class AppComponent {
  title = 'Member Management';
  constructor(private router: Router) {}
  logout() {
    // 清除本地存储中的JWT token
    localStorage.removeItem('token');
    // 重定向到登录页面
    this.router.navigate(['/login']);
  }
}