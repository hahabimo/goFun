import { Component, OnInit } from '@angular/core';
import { ItineraryEvent } from '../itineraryEvent';
import { EventService } from '../event.service';
import { FormsModule } from '@angular/forms'; // 导入 FormsModule
import { CommonModule } from '@angular/common';
import { ActivatedRoute  } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-event-list',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './event-list.component.html',
  styleUrl: './event-list.component.css'
})
export class EventListComponent implements OnInit {
  events: ItineraryEvent[] = [];
  groupedEvents: { [key: number]: ItineraryEvent[] } = {};
  itineraryId: number = 0;
  days: number[] = [];
  itineraryDays: number = 0; // 新增這一行
  isAddEventFormVisible: boolean = false;

  newEvent: Partial<ItineraryEvent> = {
    name: '',
    description: '',
    belongDay: 1,
    itineraryId: 0
  };

  constructor(private eventService: EventService, private route: ActivatedRoute,
    private location: Location) { }

  ngOnInit() {
    this.itineraryId = +this.route.snapshot.paramMap.get('id')!;
    this.itineraryDays = +this.route.snapshot.paramMap.get('days')!; // 獲取天數參數
    this.newEvent.itineraryId = this.itineraryId;
    this.loadEvents();
  }

  loadEvents() {
    this.eventService.getItineraryEvents(this.itineraryId).subscribe(events => {
      console.log(events);
      this.events = events;
      this.groupEventsByDay();
      this.initializeDays();
    });
  }

  groupEventsByDay() {
    for(let i in this.events){

    }
    this.groupedEvents = this.events.reduce((groups, event) => {
      if (!groups[event.belongDay]) {
        groups[event.belongDay] = [];
      }
      groups[event.belongDay].push(event);
      return groups;
    }, {} as { [key: number]: ItineraryEvent[] });
  }

  initializeDays() {
    if (this.events.length > 0) {
      const maxDay = Math.max(...this.events.map(e => e.belongDay));
      this.days = Array.from({ length: maxDay }, (_, i) => i + 1);
    }
  }
  showAddEventForm() {
    this.isAddEventFormVisible = true;
  }
  hideAddEventForm() {
    this.isAddEventFormVisible = false;
    this.resetNewEvent();
  }
  addEvent() {
    this.eventService.createEvent(this.newEvent as ItineraryEvent).subscribe(event => {
      if (!this.groupedEvents[event.belongDay]) {
        this.groupedEvents[event.belongDay] = [];
    }
      this.groupedEvents[event.belongDay].push(event);
    });
  }

  resetNewEvent() {
    this.newEvent = {
      name: '',
      description: '',
      belongDay: 1,
      itineraryId: this.itineraryId
    };
  }

  updateEvent(event: ItineraryEvent) {
    this.eventService.updateEvent(event.id, event).subscribe(updatedEvent => {
      const index = this.events.findIndex(e => e.id === updatedEvent.id);
      if (index !== -1) {
        this.events[index] = updatedEvent;
      }
      this.groupEventsByDay();
    });
  }

  deleteEvent(id: number) {
    this.eventService.deleteEvent(id).subscribe(() => {
      this.events = this.events.filter(event => event.id !== id);
      this.groupEventsByDay();
      this.initializeDays();
    });
  }
  goBack() {
    this.location.back(); // 返回上一頁的方法
  }
}