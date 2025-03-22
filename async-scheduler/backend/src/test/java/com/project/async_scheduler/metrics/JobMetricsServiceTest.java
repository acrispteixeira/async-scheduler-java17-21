package com.project.async_scheduler.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobMetricsServiceTest {
    private JobMetricsService jobMetricsService;
    private MeterRegistry meterRegistry;

    @BeforeEach
    void setUp() {
        meterRegistry = new SimpleMeterRegistry();
        jobMetricsService = new JobMetricsService(meterRegistry);
    }

    @Test
    void shouldIncrementJobCount() {
        jobMetricsService.incrementJobCount();
        jobMetricsService.incrementJobCount();

        double count = meterRegistry.get("jobs.executed").counter().count();
        assertEquals(2, count);
    }
}
