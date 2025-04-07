import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { JobsComponent } from './jobs.component';
import { JobsService } from './jobs.service';
import { FormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';

describe('JobsComponent', () => {
  let component: JobsComponent;
  let fixture: ComponentFixture<JobsComponent>;
  let mockJobsService: jasmine.SpyObj<JobsService>;

  beforeEach(async () => {
    mockJobsService = jasmine.createSpyObj('JobsService', ['getAllJobs', 'executeJob', 'getJobCount']);

    await TestBed.configureTestingModule({
      declarations: [JobsComponent],
      imports: [FormsModule],
      providers: [
        { provide: JobsService, useValue: mockJobsService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(JobsComponent);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should load jobs and job count on init', () => {
    const mockJobs = [{ name: 'Job1', status: 'Completed' }];
    const mockCount = 1;

    mockJobsService.getAllJobs.and.returnValue(of(mockJobs));
    mockJobsService.getJobCount.and.returnValue(of(mockCount));

    fixture.detectChanges(); // triggers ngOnInit

    expect(mockJobsService.getAllJobs).toHaveBeenCalled();
    expect(mockJobsService.getJobCount).toHaveBeenCalled();
    expect(component.jobs).toEqual(mockJobs);
    expect(component.jobCount).toBe(mockCount);
  });

  it('should execute a job and reload jobs after submission', fakeAsync(() => {
    component.jobName = 'TestJob';
    mockJobsService.executeJob.and.returnValue(of('Job TestJob executado!'));
    mockJobsService.getAllJobs.and.returnValue(of([]));
    mockJobsService.getJobCount.and.returnValue(of(0));

    component.onSubmit();
    tick();

    expect(mockJobsService.executeJob).toHaveBeenCalledWith('TestJob');
    expect(mockJobsService.getAllJobs).toHaveBeenCalled();
    expect(mockJobsService.getJobCount).toHaveBeenCalled();
    expect(component.jobName).toBe('');
  }));

  it('should not execute job if jobName is empty', () => {
    component.jobName = ' ';
    component.onSubmit();
    expect(mockJobsService.executeJob).not.toHaveBeenCalled();
  });

  it('should handle error in loadJobCount', () => {
    spyOn(console, 'error');
    mockJobsService.getAllJobs.and.returnValue(of([]));
    mockJobsService.getJobCount.and.returnValue(throwError(() => new Error('Erro simulado')));

    fixture.detectChanges();

    expect(console.error).toHaveBeenCalledWith('Erro ao carregar contador de jobs:', jasmine.any(Error));
  });

  it('should show job count in the template', () => {
    const mockCount = 3;
    mockJobsService.getAllJobs.and.returnValue(of([]));
    mockJobsService.getJobCount.and.returnValue(of(mockCount));

    fixture.detectChanges();
    const jobCountEl: DebugElement = fixture.debugElement.query(By.css('.job-counter'));
    expect(jobCountEl.nativeElement.textContent).toContain(`Executed Jobs Count: ${mockCount}`);
  });

});
