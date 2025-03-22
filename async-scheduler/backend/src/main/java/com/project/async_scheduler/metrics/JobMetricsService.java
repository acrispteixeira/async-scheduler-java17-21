package com.project.async_scheduler.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class JobMetricsService {
    private final Counter jobCounter;

    public JobMetricsService(MeterRegistry meterRegistry) {
        this.jobCounter = meterRegistry.counter("jobs.executed");
    }

    public void incrementJobCount() {
        jobCounter.increment();
    }
}
