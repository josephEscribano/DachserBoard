import { Injectable } from '@angular/core';
import { User } from '../../domain/types';
import { catchError, Observable, throwError } from 'rxjs';
import { BASE_URL } from '../../environments/environments';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private userUrl = `${BASE_URL}/users`;

  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
    return this.http
      .get<User[]>(`${this.userUrl}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any) {
    console.error('User Service Error:', error);
    return throwError(() => 'Error occurred while processing users.');
  }
}
