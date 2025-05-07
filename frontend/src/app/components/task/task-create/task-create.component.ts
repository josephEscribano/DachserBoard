import { GlobalService } from './../../../services/global.service';
import { TaskStatus, User, TaskRequest } from './../../../../domain/types';
import { Component, inject } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { UserService } from '../../../services/user.service';
import { AsyncPipe } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { Observable } from 'rxjs';
import { TaskService } from '../../../services/task.service';
import { ProjectService } from '../../../services/project.service';

@Component({
  selector: 'app-task-create',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatDialogContent,
    MatDialogActions,
    MatDialogTitle,
    MatMenuModule,
    MatChipsModule,
    MatIconModule,
    FormsModule,
    AsyncPipe,
  ],
  templateUrl: './task-create.component.html',
})
export class TaskCreateComponent {
  readonly status: TaskStatus = inject(MAT_DIALOG_DATA)?.['status'];
  readonly dialogRef = inject(MatDialogRef<TaskCreateComponent>);
  userService = inject(UserService);
  taskService = inject(TaskService);
  projectService = inject(ProjectService);
  globalService = inject(GlobalService);

  projectId: string | undefined = this.globalService.currentProjectId;

  users: Observable<User[]> = this.userService.getUsers();
  assignedUsers: User[] = [];

  taskForm = new FormGroup({
    title: new FormControl('', Validators.required),
    description: new FormControl('', Validators.required),
  });

  popUserAssigned(user: User): void {
    this.assignedUsers = this.assignedUsers?.filter((u) => u.id !== user.id);
  }

  pushUserAssigned(user: User): void {
    this.assignedUsers!.push(user);
  }

  onSubmit(): void {
    const taskRequest: TaskRequest = {
      idProject: this.projectId!,
      title: this.taskForm.value.title!,
      description: this.taskForm.value.description!,
      status: this.status,
      assignedTo: this.assignedUsers,
    };

    this.taskService.create(taskRequest).subscribe((result) => {
      this.globalService.notificationTaskModified(result.id);
      this.dialogRef.close();
    });
  }
}
