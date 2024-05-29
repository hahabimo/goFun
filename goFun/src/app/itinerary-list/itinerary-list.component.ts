import { Component, OnInit } from '@angular/core';
import { Itinerary } from '../itinerary';
import { ItineraryService } from '../itinerary.service';
import { FormsModule } from '@angular/forms'; // 导入 FormsModule
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-itinerary-list',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './itinerary-list.component.html',
  styleUrl: './itinerary-list.component.css'
})
export class ItineraryListComponent implements OnInit {
  itineraries: Itinerary[] = [];
  newItinerary: Partial<Itinerary> = {};
  itineraryIdToAdd: number | null = null; // 新增行程的ID

  constructor(
    private itineraryService: ItineraryService,
    private router: Router ) { }

  ngOnInit() {
    this.loadItineraries();
  }

  loadItineraries() {
    this.itineraryService.getUserItineraries().subscribe(itineraries => this.itineraries = itineraries);
  }

  addItinerary() {
    this.itineraryService.createItinerary(this.newItinerary as Itinerary).subscribe(itinerary => {
      this.itineraries.push(itinerary);
    });
    this.newItinerary = {};
  }

  updateItinerary(itinerary: Itinerary) {
    this.itineraryService.updateItinerary(itinerary).subscribe(updatedItinerary => {
      const index = this.itineraries.findIndex(i => i.id === updatedItinerary.id);
      if (index !== -1) {
        this.itineraries[index] = updatedItinerary;
      }
    });
  }

  deleteItinerary(id: number) {
    this.itineraryService.deleteItinerary(id).subscribe(() => {
      this.itineraries = this.itineraries.filter(itinerary => itinerary.id !== id);
    });
  }

  addExistingItinerary() {
    if (this.itineraryIdToAdd) {
      this.itineraryService.addItineraryToUser(this.itineraryIdToAdd).subscribe(itinerary => {
        this.itineraries.push(itinerary);
        this.itineraryIdToAdd = null; // 清空輸入框
      });
    }
  }

  viewEvents(itinerary: Itinerary) {
    this.router.navigate(['/itinerary', itinerary.id, 'events', itinerary.days]);
  }

  
}
