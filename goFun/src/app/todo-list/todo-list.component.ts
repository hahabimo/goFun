import { Component, OnInit } from '@angular/core';
import { TodoService } from '../todo.service';
import { Todo } from '../todo';
import { FormsModule } from '@angular/forms'; // 导入 FormsModule
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.css'],
  standalone: true,
  imports: [FormsModule,CommonModule] // 确保 FormsModule 被导入
})
export class TodoListComponent implements OnInit {
  todos: Todo[] = [];
  newTodo: Todo = { description: '', completed: false };

  constructor(private todoService: TodoService) { }

  ngOnInit(): void {
    this.getTodos();
  }

  getTodos(): void {
    this.todoService.getTodos().subscribe(todos => this.todos = todos);
  }

  createTodo(): void {
    if (this.newTodo.description) {
      this.todoService.createTodo(this.newTodo).subscribe(todo => {
        this.todos.push(todo);
        this.newTodo = { description: '', completed: false };
      });
    }
  }

  updateTodo(todo: Todo): void {
    this.todoService.updateTodo(todo).subscribe(updatedTodo => {
      const index = this.todos.findIndex(t => t.id === updatedTodo.id);
      if (index !== -1) {
        this.todos[index] = updatedTodo;
      }
    });
  }

  deleteTodo(id: number): void {
    this.todoService.deleteTodo(id).subscribe(() => {
      this.todos = this.todos.filter(todo => todo.id !== id);
    });
  }
}