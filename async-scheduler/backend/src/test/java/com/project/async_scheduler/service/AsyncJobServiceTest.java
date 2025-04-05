package com.project.async_scheduler.service;

import com.project.async_scheduler.model.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AsyncJobServiceTest {

    private JobMetricsService jobMetricsService;
    private AsyncJobService asyncJobService;

    @BeforeEach
    void setUp() {
        jobMetricsService = mock(JobMetricsService.class);
        asyncJobService = new AsyncJobService(jobMetricsService);
    }

    @Test
    void shouldExecuteJobAndReturnCompletedMessage() throws Exception {
        // Arrange
        String jobName = "TestJob";
        when(jobMetricsService.getJobCount()).thenReturn(1L);

        // Act
        CompletableFuture<String> futureResult = asyncJobService.executeJob(jobName);
        String result = futureResult.get(); // Espera a execução assíncrona

        // Assert
        assertThat(result).isEqualTo("Job TestJob finalizado!");
        verify(jobMetricsService, times(1)).incrementJobCount();
        verify(jobMetricsService, times(1)).getJobCount();

        List<Job> jobs = asyncJobService.getAllJobs();
        assertThat(jobs).hasSize(1);
        Job job = jobs.get(0);
        assertThat(job.getName()).isEqualTo(jobName);
        assertThat(job.getStatus()).isEqualTo("Completed");
        assertThat(job.getExecutions()).isEqualTo(1L);
        assertThat(job.getCreatedAt()).isNotNull();
        assertThat(job.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    void shouldReturnEmptyListWhenNoJobsExecuted() {
        // Act
        List<Job> jobs = asyncJobService.getAllJobs();

        // Assert
        assertThat(jobs).isEmpty();
    }
}
