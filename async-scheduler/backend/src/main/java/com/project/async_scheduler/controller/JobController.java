package com.project.async_scheduler.controller;

import com.project.async_scheduler.service.AsyncJobService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private final AsyncJobService jobService;

    public JobController(AsyncJobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/{jobName}")
    public CompletableFuture<String> startJob(@PathVariable String jobName) {
        return jobService.executeJob(jobName);
    }
}
