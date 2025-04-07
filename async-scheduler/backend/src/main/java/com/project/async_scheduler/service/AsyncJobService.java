package com.project.async_scheduler.service;

import com.project.async_scheduler.model.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SequencedCollection;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AsyncJobService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncJobService.class);

    private final JobMetricsService jobMetricsService;

    // SequencedCollection para manter a ordem previsível
    private final SequencedCollection<Job> jobHistory = new LinkedList<>();

    public AsyncJobService(JobMetricsService jobMetricsService) {
        this.jobMetricsService = jobMetricsService;
    }

    // Usando VirtualThreads
    public String executeJob(String jobName) {
        logger.info("Executando job: {}", jobName);
        logger.info("Thread atual: {}", Thread.currentThread());

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000)); // Simula processamento
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

        synchronized (jobHistory) {
            jobHistory.addLast(job); // mantém a ordem
        }

        return "Job " + jobName + " finalizado!";
    }

    public List<Job> getAllJobs() {
        synchronized (jobHistory) {
            return new ArrayList<>(jobHistory);
        }
    }

}
