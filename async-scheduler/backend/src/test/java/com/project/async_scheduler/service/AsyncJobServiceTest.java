package com.project.async_scheduler.service;

import com.project.async_scheduler.model.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AsyncJobServiceTest {

    private JobMetricsService jobMetricsService;
    private AsyncJobService asyncJobService;

    @BeforeEach
    void setUp() {
        jobMetricsService = mock(JobMetricsService.class);
        asyncJobService = new AsyncJobService(jobMetricsService);
    }

    @Test
    void shouldExecuteJobAndRegisterInHistory() {
        // Arrange
        String jobName = "TestJob";
        when(jobMetricsService.getJobCount()).thenReturn(1L);

        // Act
        String result = asyncJobService.executeJob(jobName);

        // Assert
        assertThat(result).isEqualTo("Job TestJob finalizado!");
        verify(jobMetricsService, times(1)).incrementJobCount();
        verify(jobMetricsService, times(1)).getJobCount();

        List<Job> jobs = asyncJobService.getAllJobs();
        assertThat(jobs).hasSize(1);
        Job job = jobs.getFirst();
        assertThat(job.getName()).isEqualTo(jobName);
        assertThat(job.getStatus()).isEqualTo("Completed");
        assertThat(job.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    void shouldReturnEmptyJobHistoryInitially() {
        // Act
        List<Job> jobs = asyncJobService.getAllJobs();

        // Assert
        assertThat(jobs).isEmpty();
    }
}
