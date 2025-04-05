package com.project.async_scheduler.controller;

import com.project.async_scheduler.service.JobMetricsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.ResponseEntity;

public class JobMetricsControllerTest {

    private JobMetricsService jobMetricsService;
    private JobMetricsController jobMetricsController;

    @BeforeEach
    void setUp() {
        jobMetricsService = Mockito.mock(JobMetricsService.class);
        jobMetricsController = new JobMetricsController(jobMetricsService);
    }

    @Test
    void shouldReturnJobCountSuccessfully() {
        // Arrange
        long expectedCount = 42L;
        when(jobMetricsService.getJobCount()).thenReturn(expectedCount);

        // Act
        ResponseEntity<Long> response = jobMetricsController.getJobCount();

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(expectedCount);
    }
}
