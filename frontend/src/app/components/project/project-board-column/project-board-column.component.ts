import {
  CdkDrag,
  CdkDragDrop,
  CdkDropList,
  DragDropModule,
} from '@angular/cdk/drag-drop';
import { Component, EventEmitter, inject, input, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { TaskCardComponent } from '../../task/task-card/task-card.component';
import { Task, TaskStatus } from '../../../../domain/types';
import { MatDialog } from '@angular/material/dialog';
import { TaskService } from '../../../services/task.service';
import { TaskCreateComponent } from '../../task/task-create/task-create.component';

@Component({
  selector: 'app-project-board-column',
  standalone: true,
  imports: [
    DragDropModule,
    CdkDropList,
    CdkDrag,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    TaskCardComponent,
  ],
  templateUrl: './project-board-column.component.html',
})
export class ProjectBoardColumnComponent {
  readonly dialog = inject(MatDialog);
  taskService = inject(TaskService);
  columnName = input.required<string>();
  columnStatus = input.required<TaskStatus>();
  tasks = input.required<Task[]>();

  @Output() onDrop = new EventEmitter<CdkDragDrop<Task[]>>();

  openProjecCreateDialog(): void {
    this.dialog.open(TaskCreateComponent, {
      width: '500px',
      data: { status: this.columnStatus() },
    });
  }
}
