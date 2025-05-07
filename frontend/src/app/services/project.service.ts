import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Project, ProjectRequest } from '../../domain/types';
import { BASE_URL } from '../../environments/environments';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  private projectUrl = `${BASE_URL}/projects`;
  constructor(private http: HttpClient) {}

  getAll(): Observable<Project[]> {
    return this.http
      .get<Project[]>(this.projectUrl)
      .pipe(catchError(this.handleError));
  }

  getById(id: string): Observable<Project> {
    return this.http
      .get<Project>(`${this.projectUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  create(project: ProjectRequest): Observable<Project> {
    return this.http
      .post<Project>(this.projectUrl, project)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any) {
    console.error('Project Service Error:', error);
    return throwError(() => 'Error occurred while processing projects.');
  }
}

