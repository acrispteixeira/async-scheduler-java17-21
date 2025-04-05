import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class JobsService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getAllJobs(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/getAllJobs`);
  }

  executeJob(jobName: string): Observable<string> {
    return this.http.post(`${this.apiUrl}/${jobName}`, {}, { responseType: 'text'});
  }

  getJobCount(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/metrics/jobCount`);
  }
  
}