import { Component, OnInit } from '@angular/core';
import { JobsService } from './jobs.service';

@Component({
  selector: 'app-jobs',
  template: `
    <div class="container">
      <h2>Run Jobs</h2>

      <form (ngSubmit)="onSubmit()" #jobForm="ngForm" class="form">
        <div class="form-group">
          <label for="jobName">Job Name:</label>
          <input
            type="text"
            id="jobName"
            name="jobName"
            [(ngModel)]="jobName"
            required
          />
        </div>

        <button type="submit" [disabled]="!jobForm.form.valid">
          Execute Job
        </button>
      </form>

      <div class="job-list">
        <h3>Executed Jobs:</h3>
        <ul>
          <li *ngFor="let job of jobs" class="completed">
            <span class="job-name">{{ job.name }}</span>
            <span class="job-status">✔ {{ job.status }}</span>
          </li>
        </ul>
      </div>
      <div class="job-count">
        <p class="job-counter">
        Executed Jobs Count: <strong>{{ jobCount }}</strong>
        </p>
      </div>

    </div>
  `,
  styles: [`
    .container {
      max-width: 600px;
      margin: 2rem auto;
      padding: 1.5rem;
      background-color: #fff;
      border-radius: 1rem;
      box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    }

    h2 {
      font-size: 1.75rem;
      font-weight: bold;
      margin-bottom: 1rem;
      color: #2c3e50;
    }

    .form {
      display: flex;
      flex-direction: column;
      gap: 1rem;
    }

    .form-group {
      display: flex;
      flex-direction: column;
    }

    label {
      font-weight: 600;
      margin-bottom: 0.5rem;
    }

    input {
      padding: 0.5rem;
      border: 1px solid #ccc;
      border-radius: 0.375rem;
    }

    button {
      align-self: flex-start;
      padding: 0.5rem 1.25rem;
      background-color: #4f46e5;
      color: #fff;
      border: none;
      border-radius: 0.375rem;
      cursor: pointer;
      transition: background-color 0.3s;
    }

    button:disabled {
      background-color: #a5b4fc;
      cursor: not-allowed;
    }

    .job-list {
      margin-top: 2rem;
    }

    .job-list h3 {
      font-size: 1.25rem;
      margin-bottom: 0.75rem;
      color: #374151;
    }

    ul {
      list-style: none;
      padding: 0;
    }

    li {
      display: flex;
      justify-content: space-between;
      padding: 0.75rem;
      border-radius: 0.5rem;
      background-color: #f9fafb;
      margin-bottom: 0.5rem;
    }
    
    .completed {
      padding: 8px;
      background-color: #e8f5e9;
      border-left: 4px solid #4caf50;
      margin-bottom: 6px;
      border-radius: 4px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .job-name {
      font-weight: 500;
      color: #2c3e50;
    }

    .job-status {
      font-size: 0.9em;
      color: #4caf50;
    }

    .job-counter {
      font-size: 1rem;
      margin-bottom: 1rem;
      color: #1e293b;
    }
  `]
})
export class JobsComponent implements OnInit {
  jobs: any[] = [];
  jobName: string = '';
  jobCount: number = 0;

  constructor(private jobsService: JobsService) {}

  ngOnInit() {
    this.loadJobs();
  }

  loadJobs() {
    this.jobsService.getAllJobs().subscribe(data => this.jobs = data);
    this.loadJobCount();
  }

  loadJobCount(): void {
    this.jobsService.getJobCount().subscribe({
      next: (count) => this.jobCount = count,
      error: (err) => console.error('Erro ao carregar contador de jobs:', err)
    });
  }

  onSubmit() {    
    if (this.jobName.trim()) {
      this.jobsService.executeJob(this.jobName).subscribe({
        next: (res: any) => {
          console.log(`${res}`);
          this.loadJobs();
          this.jobName = '';
        },
        error: (err) => {
          console.error('Erro ao executar job:', err.message || err);
        },
        complete: () => {
          console.log('Requisição finalizada');
        }
      });
    }
  }

}