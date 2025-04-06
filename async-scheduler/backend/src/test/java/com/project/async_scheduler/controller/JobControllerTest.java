package com.project.async_scheduler.controller;

import com.project.async_scheduler.model.Job;
import com.project.async_scheduler.service.AsyncJobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class JobControllerTest {

    private AsyncJobService jobService;
    private JobController jobController;

    @BeforeEach
    void setUp() {
        jobService = mock(AsyncJobService.class);
        jobController = new JobController(jobService);
    }

    @Test
    void shouldExecuteJobAndReturnCompletableFutureWithResult() throws Exception {
        // Arrange
        String jobName = "testJob";
        String expectedResponse = "Job executed successfully";
        CompletableFuture<String> futureResponse = CompletableFuture.completedFuture(expectedResponse);

        when(jobService.executeJob(jobName)).thenReturn(futureResponse);

        // Act
        CompletableFuture<String> result = jobController.executeJob(jobName);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.get()).isEqualTo(expectedResponse);
        verify(jobService, times(1)).executeJob(jobName);
    }

    @Test
    void shouldReturnListOfAllJobs() {
        // Arrange
        List<Job> mockJobs = List.of(
                new Job("Job1", "PENDING"),
                new Job("Job2", "COMPLETED")
        );

        when(jobService.getAllJobs()).thenReturn(mockJobs);

        // Act
        List<Job> result = jobController.getAllJobs();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Job1");
        assertThat(result.get(1).getStatus()).isEqualTo("COMPLETED");
        verify(jobService, times(1)).getAllJobs();
    }
}