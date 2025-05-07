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

  private handleError(error: any) {
    console.error('Task Service Error:', error);
    return throwError(() => 'Error occurred while processing tasks.');
  }

  notifyTaskChanges(): void {
    this.taskChangesNotificator.set(undefined);
  }
}
