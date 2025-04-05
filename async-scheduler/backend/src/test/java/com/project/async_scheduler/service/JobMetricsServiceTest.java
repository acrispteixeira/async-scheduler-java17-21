package com.project.async_scheduler.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JobMetricsServiceTest {

    private Counter counter;
    private JobMetricsService jobMetricsService;

    @BeforeEach
    void setUp() {
        MeterRegistry meterRegistry = mock(MeterRegistry.class);
        counter = mock(Counter.class);

        when(meterRegistry.counter("jobs.executed")).thenReturn(counter);
        when(counter.count()).thenReturn(3.0);

        jobMetricsService = new JobMetricsService(meterRegistry);
    }

    @Test
    void testIncrementJobCount() {
        jobMetricsService.incrementJobCount();
        verify(counter, times(1)).increment();
    }

    @Test
    void testGetJobCount() {
        long count = jobMetricsService.getJobCount();
        assertEquals(3L, count);
    }
}
