import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class GlobalService {
  private _projectId = signal<string | undefined>(undefined);
  readonly projectId = this._projectId.asReadonly();

  private _taskModified = signal<string | null>(null);
  readonly taskModifiedSignal = this._taskModified.asReadonly();

  setProjectId(id: string | undefined): void {
    this._projectId.set(id);
  }

  get currentProjectId(): string | undefined {
    return this._projectId();
  }

  notificationTaskModified(taskId: string) {
    this._taskModified.set(taskId);
  }
}
