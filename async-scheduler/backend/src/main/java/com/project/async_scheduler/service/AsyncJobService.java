package com.project.async_scheduler.service;
import com.project.async_scheduler.model.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AsyncJobService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncJobService.class);

    private final JobMetricsService jobMetricsService;
    private final Map<String, Job> jobRegistry = new ConcurrentHashMap<>();

    public AsyncJobService(JobMetricsService jobMetricsService) {
        this.jobMetricsService = jobMetricsService;
    }

    // @Async serves to execute the job asynchronously using a thread pool
    @Async
    public CompletableFuture<String> executeJob(String jobName) {

        logger.info("Executando job: {}", jobName);

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000)); // Simula um tempo de execução
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        jobMetricsService.incrementJobCount();

        Job job = new Job(
                jobName,
                "Completed",
                LocalDateTime.now(),
                jobMetricsService.getJobCount(),
                LocalDateTime.now()
        );

        jobRegistry.put(jobName, job);

        return CompletableFuture.completedFuture("Job " + jobName + " finalizado!");
    }

    public List<Job> getAllJobs() {
        return new ArrayList<>(jobRegistry.values());
    }

}
