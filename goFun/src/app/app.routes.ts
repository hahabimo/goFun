import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { TodoListComponent } from './todo-list/todo-list.component';
import { ItineraryListComponent } from './itinerary-list/itinerary-list.component';
import { EventListComponent } from './event-list/event-list.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'todos', component: TodoListComponent },
  { path: 'itineraries', component: ItineraryListComponent },
  { path: 'itinerary/:id/events/:days', component: EventListComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' } // 默认导航到登录页面
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }