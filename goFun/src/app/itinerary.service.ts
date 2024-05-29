import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Itinerary } from './itinerary';

@Injectable({
  providedIn: 'root'
})
export class ItineraryService {
  private baseUrl = 'http://localhost:8080/itineraries';

  constructor(private http: HttpClient) { }

  getUserItineraries(): Observable<Itinerary[]> {
    return this.http.get<Itinerary[]>(this.baseUrl, this.getHttpOptions());
  }

  createItinerary(itinerary: Itinerary): Observable<Itinerary> {
    console.log(itinerary);
    return this.http.post<Itinerary>(this.baseUrl, itinerary, this.getHttpOptions());
  }

  updateItinerary(itinerary: Itinerary): Observable<Itinerary> {
    return this.http.put<Itinerary>(`${this.baseUrl}/${itinerary.id}`, itinerary, this.getHttpOptions());
  }

  deleteItinerary(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, this.getHttpOptions());
  }

  addItineraryToUser(id: number): Observable<Itinerary> {
    return this.http.post<Itinerary>(`${this.baseUrl}/addItinerary/${id}`, this.getHttpOptions());
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
