import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ItineraryEvent } from './itineraryEvent'

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private baseUrl = 'http://localhost:8080/events';

  constructor(private http: HttpClient) { }

  getItineraryEvents(itineraryId: number): Observable<ItineraryEvent[]> {
    return this.http.get<ItineraryEvent[]>(`${this.baseUrl}/itinerary/${itineraryId}`, this.getHttpOptions());
  }

  createEvent(event: ItineraryEvent): Observable<ItineraryEvent> {
    console.log(event);
    return this.http.post<ItineraryEvent>(this.baseUrl, event, this.getHttpOptions());
  }

  updateEvent(id: number, event: ItineraryEvent): Observable<ItineraryEvent> {
    return this.http.put<ItineraryEvent>(`${this.baseUrl}/${id}`, event, this.getHttpOptions());
  }

  deleteEvent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, this.getHttpOptions());
  }

  private getHttpOptions() {
    const token = localStorage.getItem('token');
    return {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      })
    };
  }
}
