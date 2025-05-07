import { Component, effect, inject, input } from '@angular/core';
import { ProjectService } from '../../../services/project.service';
import {
  PatchTask,
  Project,
  Task,
  TaskStatus,
  TaskStatusValues,
} from '../../../../domain/types';

import {
  CdkDragDrop,
  moveItemInArray,
  transferArrayItem,
  DragDropModule,
} from '@angular/cdk/drag-drop';
import { ProjectBoardColumnComponent } from '../../../components/project/project-board-column/project-board-column.component';
import { TaskService } from '../../../services/task.service';
import { BehaviorSubject } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { GlobalService } from '../../../services/global.service';

@Component({
  selector: 'app-project-detail',
  standalone: true,
  imports: [DragDropModule, ProjectBoardColumnComponent, AsyncPipe],
  templateUrl: './project-detail.component.html',
})
export class ProjectDetailComponent {
  projectId = input<string>();

  projectService = inject(ProjectService);
  taskService = inject(TaskService);
  globalService = inject(GlobalService);

  projectData: Partial<Project> = {};
  showLoading = new BehaviorSubject<boolean>(true);
  taskData: { status: TaskStatus; tasks: Task[] }[] = [];

  columns: { name: string; status: TaskStatus }[] = [
    { name: 'To Do', status: 'TO_DO' },
    { name: 'In Progress', status: 'IN_PROGRESS' },
    { name: 'On Hold', status: 'ON_HOLD' },
    { name: 'Done', status: 'DONE' },
  ];

  constructor() {
    effect(
      () => {
        this.getProjectInfo();
        this.getTaskByProject();
        this.globalService.setProjectId(this.projectId());
      },
      { allowSignalWrites: true }
    );

    effect(() => {
      this.globalService.taskModifiedSignal();
      this.getTaskByProject();
    });
  }

  getProjectInfo(): void {
    this.projectService.getById(this.projectId()!).subscribe({
      next: (project) => {
        this.projectData = project;
      },
    });
  }

  getTaskByProject(): void {
    this.taskData = [];
    this.showLoading.next(true);
    this.taskService.getByProjectId(this.projectId()!).subscribe({
      next: (tasks) => {
        this.populateTasks(tasks);
        this.showLoading.next(false);
      },
    });
  }

  populateTasks(tasks: Task[]): void {
    TaskStatusValues.forEach((taskStatus) => {
      this.taskData.push({
        status: taskStatus,
        tasks: tasks.filter((t) => t.status === taskStatus),
      });
    });
  }

  filterTask(status: TaskStatus): Task[] {
    if (this.taskData.length === 0) return [];
    return this.taskData.find((tk) => tk.status === status)!.tasks;
  }

  getStatusFromColumnId(columnId: string): TaskStatus {
    switch (columnId) {
      case TaskStatusValues[0]:
        return TaskStatusValues[0];
      case TaskStatusValues[1]:
        return TaskStatusValues[1];
      case TaskStatusValues[2]:
        return TaskStatusValues[2];
      case TaskStatusValues[3]:
        return TaskStatusValues[3];
      default:
        return 'TO_DO';
    }
  }

  drop(event: CdkDragDrop<Task[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );

      const patchTask: PatchTask = {
        title: event.item.data.title,
        description: event.item.data.description,
        status: this.getStatusFromColumnId(event.container.id),
        assignedTo: event.item.data.assignedTo,
      };
      this.taskService
        .update(patchTask, event.item.data.id)
        .subscribe((result) =>
          this.globalService.notificationTaskModified(event.container.id)
        );
    }
  }
}
