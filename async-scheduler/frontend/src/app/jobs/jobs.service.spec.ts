import { TestBed } from '@angular/core/testing';
import { JobsService } from './jobs.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('JobsService', () => {
  let service: JobsService;
  let httpMock: HttpTestingController;

  const apiUrl = 'http://localhost:8080';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [JobsService]
    });
    service = TestBed.inject(JobsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); 
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all jobs', () => {
    const mockJobs = [
      { name: 'Job1', status: 'Completed' },
      { name: 'Job2', status: 'Completed' }
    ];

    service.getAllJobs().subscribe(jobs => {
      expect(jobs.length).toBe(2);
      expect(jobs).toEqual(mockJobs);
    });

    const req = httpMock.expectOne(`${apiUrl}/getAllJobs`);
    expect(req.request.method).toBe('GET');
    req.flush(mockJobs);
  });

  it('should execute a job', () => {
    const jobName = 'TestJob';
    const mockResponse = `Job ${jobName} finalizado!`;

    service.executeJob(jobName).subscribe(response => {
      expect(response).toBe(mockResponse);
    });

    const req = httpMock.expectOne(`${apiUrl}/${jobName}`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({});
    req.flush(mockResponse, { status: 200, statusText: 'OK' });
  });

  it('should get job count', () => {
    const mockCount = 5;

    service.getJobCount().subscribe(count => {
      expect(count).toBe(mockCount);
    });

    const req = httpMock.expectOne(`${apiUrl}/metrics/jobCount`);
    expect(req.request.method).toBe('GET');
    req.flush(mockCount);
  });
});
