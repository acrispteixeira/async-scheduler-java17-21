package com.project.async_scheduler.scheduler;

import com.project.async_scheduler.metrics.JobMetricsService;
import com.project.async_scheduler.service.AsyncJobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AsyncJobServiceTest {
    private AsyncJobService asyncJobService;
    private JobMetricsService jobMetricsService;

    @BeforeEach
    void setUp() {
        jobMetricsService = Mockito.mock(JobMetricsService.class);
        asyncJobService = new AsyncJobService(jobMetricsService);
    }

    @Test
    void shouldExecuteJobAndIncrementCounter() {
        String jobName = "TestJob";

        CompletableFuture<String> future = asyncJobService.executeJob(jobName);
        String result = future.join(); // Aguarda a execução assíncrona

        assertEquals("Job " + jobName + " finalizado!", result);
        verify(jobMetricsService, times(1)).incrementJobCount();
    }
}
