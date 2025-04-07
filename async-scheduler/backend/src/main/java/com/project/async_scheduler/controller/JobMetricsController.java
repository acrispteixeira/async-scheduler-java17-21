package com.project.async_scheduler.controller;

import com.project.async_scheduler.service.JobMetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
public class JobMetricsController {

    private final JobMetricsService jobMetricsService;

    public JobMetricsController(JobMetricsService jobMetricsService) {
        this.jobMetricsService = jobMetricsService;
    }

    @GetMapping("/jobCount")
    public ResponseEntity<Long> getJobCount() {
        return ResponseEntity.ok(jobMetricsService.getJobCount());
    }
}
