import { Injectable, signal } from '@angular/core';
import { BehaviorSubject, catchError, Observable, of, throwError } from 'rxjs';
import { PatchTask, Task, TaskRequest, TaskStatus } from '../../domain/types';
import { BASE_URL } from '../../environments/environments';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private tasksUrl = `${BASE_URL}/tasks`;

  private taskChangesNotificator = signal<void | undefined>(undefined);

  constructor(private http: HttpClient) {}

  getById(id: string): Observable<Task> {
    return this.http
      .get<Task>(`${this.tasksUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  getByProjectId(idProject: string): Observable<Task[]> {
    return this.http
      .get<Task[]>(`${this.tasksUrl}/projectId/${idProject}`)
      .pipe(catchError(this.handleError));
  }

  create(task: TaskRequest): Observable<Task> {
    this.notifyTaskChanges();
    return this.http
      .post<Task>(this.tasksUrl, task)
      .pipe(catchError(this.handleError));
  }

  update(patchTask: PatchTask, id: string): Observable<Task> {
    this.notifyTaskChanges();
    return this.http
      .patch<Task>(`${this.tasksUrl}/taskId/${id}`, patchTask)
      .pipe(catchError(this.handleError));
  }

  updateState(id: string, status: TaskStatus): Observable<void> {
    this.notifyTaskChanges();
    return of();
  }

  private handleError(error: any) {
    console.error('Task Service Error:', error);
    return throwError(() => 'Error occurred while processing tasks.');
  }

  notifyTaskChanges(): void {
    this.taskChangesNotificator.set(undefined);
  }
}

/* import { Injectable } from '@angular/core';

import { BehaviorSubject, Observable, of } from 'rxjs';

import { TasksMockData } from '../../domain/mock-data';

import { Task, TaskStatus } from '../../domain/types';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private taskSource = TasksMockData;

  private taskState = new BehaviorSubject(this.taskSource); // Replace this BehaviorSubject by signal

  public taskChangesNotificator = new BehaviorSubject<void>(undefined);

  getById(id: string): Observable<Task> {
    return of(this.taskState.value.find((t) => t.id === id)!);
  }

  getByProjectId(idProject: string): Observable<Task[]> {
    return of(this.taskState.value.filter((t) => t.idProject === idProject));
  }

  create(task: Task): Observable<Task> {
    this.taskState.next([...this.taskState.value, task]);

    this.taskChangesNotificator.next(undefined);

    return of();
  }

  update(task: Task): Observable<Task> {
    const updatedSource = this.taskState.value.map((t) => {
      if (t.id === task.id) return task;

      return t;
    });

    this.taskState.next(updatedSource);

    this.taskChangesNotificator.next(undefined);

    return of();
  }

  updateState(id: string, status: TaskStatus): Observable<void> {
    const currentTask = this.taskState.value.find((t) => t.id === id)!;

    currentTask.status = status;

    const updatedSource = this.taskState.value.map((t) => {
      if (t.id === id) return currentTask;

      return t;
    });

    this.taskState.next(updatedSource);

    return of();
  }
}
 */
