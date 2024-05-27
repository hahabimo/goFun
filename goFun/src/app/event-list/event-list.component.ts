import { Component, OnInit } from '@angular/core';
import { Itinerary } from '../itinerary';
import { ItineraryService } from '../itinerary.service';
import { Event } from '../event';
import { EventService } from '../event.service';
import { FormsModule } from '@angular/forms'; // 导入 FormsModule
import { CommonModule } from '@angular/common';
import { ActivatedRoute  } from '@angular/router';

@Component({
  selector: 'app-event-list',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './event-list.component.html',
  styleUrl: './event-list.component.css'
})
export class EventListComponent implements OnInit {
  events: Event[] = [];

  constructor(
    private eventService: EventService,
    private itineraryService: ItineraryService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    // const itineraryId = +(this.route.snapshot.paramMap.get('id')!);;
    // this.loadItinerary(itineraryId);
    // this.loadEvents(itineraryId);
  }

  // loadItinerary(itineraryId: number) {
  //   this.itineraryService.getUserItineraries(itineraryId).subscribe(itinerary => this.itinerary = itinerary[0]); // 假設每個使用者只有一個行程
  // }

  // loadEvents(itineraryId: number) {
  //   this.eventService.getItineraryEvents(itineraryId).subscribe(events => this.events = events);
  // }

  // addEvent() {
  //   const newEvent: Partial<Event> = { name: '新事件', description: '描述', startDate: '2024-06-01', endDate: '2024-06-02', itineraryId: this.itinerary.id }; // 新事件資料
  //   this.eventService.createEvent(newEvent as Event).subscribe(event => {
  //     this.events.push(event);
  //   });
  // }

  // updateEvent(event: Event) {
  //   this.eventService.updateEvent(event).subscribe(updatedEvent => {
  //     const index = this.events.findIndex(e => e.id === updatedEvent.id);
  //     if (index !== -1) {
  //       this.events[index] = updatedEvent;
  //     }
  //   });
  // }

  // deleteEvent(id: number) {
  //   this.eventService.deleteEvent(id).subscribe(() => {
  //     this.events = this.events.filter(event => event.id !== id);
  //   });
  // }
}