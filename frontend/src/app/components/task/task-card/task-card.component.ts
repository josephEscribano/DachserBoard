import { Component, inject, input } from '@angular/core';
import { Task, User } from '../../../../domain/types';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { TaskDetailDialogComponent } from '../task-detail-dialog/task-detail-dialog.component';
import { MatChipsModule } from '@angular/material/chips';

@Component({
  selector: 'app-task-card',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatIconModule, MatChipsModule],
  templateUrl: './task-card.component.html',
})
export class TaskCardComponent {
  task = input.required<Task>();
  readonly dialog = inject(MatDialog);

  openTaskDetailDialog(): void {
    this.dialog.open(TaskDetailDialogComponent, {
      width: '500px',
      data: this.task(),
    });
  }

  getInitials(user: User): string {
    const numOfParts = user.name.split(' ');
    if (numOfParts.length > 3) {
      return numOfParts[0].substring(0, 1) + numOfParts[2].substring(0, 1);
    } else if (numOfParts.length === 2) {
      return numOfParts[0].substring(0, 1) + numOfParts[1].substring(0, 1);
    } else {
      return numOfParts[0].substring(0, 2);
    }
  }
}
