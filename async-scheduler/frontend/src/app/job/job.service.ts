import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class JobService {
  private apiUrl = 'http://localhost:8080/jobs';

  constructor(private http: HttpClient) {}

  getJobs(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }
}