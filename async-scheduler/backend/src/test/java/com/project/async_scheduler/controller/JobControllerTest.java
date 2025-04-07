package com.project.async_scheduler.controller;

import com.project.async_scheduler.model.Job;
import com.project.async_scheduler.service.AsyncJobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

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
    void shouldExecuteJobAndReturnMessage() {
        // Arrange
        String jobName = "TestJob";
        String expectedResponse = "Job TestJob finalizado!";
        when(jobService.executeJob(jobName)).thenReturn(expectedResponse);

        // Act
        String response = jobController.executeJob(jobName);

        // Assert
        assertThat(response).isEqualTo(expectedResponse);
        verify(jobService, times(1)).executeJob(jobName);
    }

    @Test
    void shouldReturnAllJobs() {
        // Arrange
        List<Job> jobList = List.of(
                new Job("Job1", "Completed", LocalDateTime.now().plusHours(1), 1, LocalDateTime.now())
        );
        when(jobService.getAllJobs()).thenReturn(jobList);

        // Act
        List<Job> result = jobController.getAllJobs();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Job1");
        verify(jobService, times(1)).getAllJobs();
    }
}