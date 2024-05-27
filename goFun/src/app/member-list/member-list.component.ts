import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MemberService } from '../member.service';
import { Member } from '../member';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-member-list',
  templateUrl: './member-list.component.html',
  styleUrls: ['./member-list.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class MemberListComponent implements OnInit {
  members: Member[] = [];
  newMember: Member = { id: 0, name: '', email: '', password: '' };
  selectedMember: Member | null = null;

  constructor(private memberService: MemberService) { }

  ngOnInit(): void {
    this.getMembers();
  }

  getMembers(): void {
    this.memberService.getMembers().subscribe(members => this.members = members);
  }

  createMember(): void {
    this.memberService.createMember(this.newMember).subscribe(member => {
      this.members.push(member);
      this.newMember = { id: 0, name: '', email: '', password: '' };
    });
  }

  editMember(member: Member): void {
    this.selectedMember = { ...member };
  }

  updateMember(): void {
    if (this.selectedMember) {
      this.memberService.updateMember(this.selectedMember.id, this.selectedMember).subscribe(updatedMember => {
        const index = this.members.findIndex(m => m.id === updatedMember.id);
        if (index !== -1) {
          this.members[index] = updatedMember;
        }
        this.selectedMember = null;
      });
    }
  }

  deleteMember(id: number): void {
    this.memberService.deleteMember(id).subscribe(() => {
      this.members = this.members.filter(member => member.id !== id);
    });
  }

  cancelEdit(): void {
    this.selectedMember = null;
  }
}