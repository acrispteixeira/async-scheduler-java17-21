package com.project.async_scheduler.controller;

import com.project.async_scheduler.service.AsyncJobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class JobControllerTest {private JobController jobController;
    private AsyncJobService asyncJobService;

    @BeforeEach
    void setUp() {
        asyncJobService = Mockito.mock(AsyncJobService.class);
        jobController = new JobController(asyncJobService);
    }

    @Test
    void shouldStartJobAndReturnMessage() {
        String jobName = "TestJob";
        when(asyncJobService.executeJob(anyString())).thenReturn(CompletableFuture.completedFuture("Job " + jobName + " finalizado!"));

        CompletableFuture<String> response = jobController.startJob(jobName);
        assertEquals("Job " + jobName + " finalizado!", response.join());
    }
}
