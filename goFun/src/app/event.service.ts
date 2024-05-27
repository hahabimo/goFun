import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Event } from './event'

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private baseUrl = 'http://localhost:8080/events';

  constructor(private http: HttpClient) { }

  getItineraryEvents(itineraryId: number): Observable<Event[]> {
    return this.http.get<Event[]>(`${this.baseUrl}/itinerary/${itineraryId}`, this.getHttpOptions());
  }

  createEvent(event: Event): Observable<Event> {
    return this.http.post<Event>(this.baseUrl, event, this.getHttpOptions());
  }

  updateEvent(event: Event): Observable<Event> {
    return this.http.put<Event>(`${this.baseUrl}/${event.id}`, event, this.getHttpOptions());
  }

  deleteEvent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, this.getHttpOptions());
  }

  private getHttpOptions() {
    const token = localStorage.getItem('token');
    return {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${token}`
      })
    };
  }
}
