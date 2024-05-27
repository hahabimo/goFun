import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { FormsModule } from '@angular/forms'; // 导入 FormsModule
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  standalone: true,
  imports: [FormsModule,CommonModule] // 确保 FormsModule 被导入
})
export class RegisterComponent {
  email: string = '';
  password: string = '';
  name: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  register(): void {
    this.authService.register(this.name, this.email, this.password).subscribe({
      next: (response) => {
        console.log('Registration successful', response);
        this.router.navigate(['/login']);
      },
      error: (error) => {
        if (error.status === 409) {
          this.errorMessage = 'Registration failed: Email already exists';
        } else {
          this.errorMessage = 'Registration failed: ' + error;
        }
      },
      complete: () => {
        this.resetForm();
      }
    });
  }

  resetForm(): void {
    this.email = '';
    this.password = '';
    this.name = '';
  }
}