import { Component, OnInit } from '@angular/core';
import { JobsService } from './jobs.service';

@Component({
  selector: 'app-jobs',
  template: `
    <h2>Scheduled Jobs</h2>
    <ul>
      <li *ngFor="let job of jobs">{{ job.name }} - {{ job.status }}</li>
    </ul>
  `,
  styles: ['h2 { color: #2c3e50; }']
})
export class JobsComponent implements OnInit {
  jobs: any[] = [];

  constructor(private jobsService: JobsService) {}

  ngOnInit() {
    this.jobsService.getJobs().subscribe(data => this.jobs = data);
  }
}