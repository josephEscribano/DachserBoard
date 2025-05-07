import { Component, inject } from '@angular/core';
import { PatchTask, Task, User } from '../../../../domain/types';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogModule,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { TaskService } from '../../../services/task.service';
import { MatMenuModule } from '@angular/material/menu';
import { UserService } from '../../../services/user.service';
import { AsyncPipe } from '@angular/common';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-task-detail-dialog',
  standalone: true,
  imports: [
    MatDialogModule,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatDialogTitle,
    MatButtonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatChipsModule,
    MatIconModule,
    MatMenuModule,
    AsyncPipe,
  ],
  templateUrl: './task-detail-dialog.component.html',
})
export class TaskDetailDialogComponent {
  readonly task = inject<Task>(MAT_DIALOG_DATA);
  readonly dialogRef = inject(MatDialogRef<TaskDetailDialogComponent>);
  taskService = inject(TaskService);
  userService = inject(UserService);

  users: Observable<User[]> = this.userService.getUsers();
  editModeTitle = false;
  assignedUsers: User[] | undefined = this.task.assignedTo
    ? JSON.parse(JSON.stringify(this.task.assignedTo))
    : [];

  taskForm = new FormGroup({
    title: new FormControl(this.task.title, Validators.required),
    description: new FormControl(this.task.description, Validators.required),
  });

  pushUserAssigned(user: User): void {
    if (!this.assignedUsers) this.assignedUsers = [];
    this.assignedUsers!.push(user);
  }

  popUserAssigned(user: User): void {
    this.assignedUsers = this.assignedUsers?.filter((u) => u.id !== user.id);
  }

  onSubmit(): void {
    const updatedTask: PatchTask = this.task;
    updatedTask.title = this.taskForm.value.title!;
    updatedTask.description = this.taskForm.value.description!;
    updatedTask.assignedTo = this.assignedUsers;

    this.taskService.update(updatedTask, this.task.id).subscribe();

    this.dialogRef.close();
  }
}
