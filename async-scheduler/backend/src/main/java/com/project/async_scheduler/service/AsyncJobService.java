package com.project.async_scheduler.service;
import com.project.async_scheduler.model.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Service
public class AsyncJobService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncJobService.class);

    private final JobMetricsService jobMetricsService;

    // SequencedCollection para manter a ordem previsível
    private final SequencedCollection<Job> jobHistory = new LinkedList<>();


    //private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public AsyncJobService(JobMetricsService jobMetricsService) {
        this.jobMetricsService = jobMetricsService;
    }

    /*
     // Usando supplyAsync
    @Async
    public CompletableFuture<String> executeJob(String jobName) {

        return CompletableFuture.supplyAsync(() -> {
            logger.info("Executando job com virtual thread: {}", jobName);

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
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

            logger.info("Job {} finalizado.", jobName);
            return "Job " + jobName + " finalizado!";
        }, executor);
    } */

    // Usando VirtualThreads
    public String executeJob(String jobName) {
        logger.info("Executando job: {}", jobName);

        System.out.println("Thread atual: " + Thread.currentThread());


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
