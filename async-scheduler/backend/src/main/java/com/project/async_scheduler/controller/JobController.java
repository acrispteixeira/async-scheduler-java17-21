package com.project.async_scheduler.controller;

import com.project.async_scheduler.model.Job;
import com.project.async_scheduler.service.AsyncJobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/")
public class JobController {
    private final AsyncJobService jobService;

    public JobController(AsyncJobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/{jobName}")
    public CompletableFuture<String> executeJob(@PathVariable String jobName) {
        return jobService.executeJob(jobName);
    }

    @GetMapping("/getAllJobs")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }
}
