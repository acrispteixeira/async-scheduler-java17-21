package com.project.async_scheduler.model;

import java.time.LocalDateTime;

public class Job {
    private String name;
    private String status;
    private LocalDateTime nextExecution;
    private long executions;
    private LocalDateTime createdAt;

    public Job(String name, String status, LocalDateTime nextExecution, long executions, LocalDateTime createdAt) {
        this.name = name;
        this.status = status;
        this.nextExecution = nextExecution;
        this.executions = executions;
        this.createdAt = createdAt;
    }

    public Job(String job1, String pending) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getNextExecution() {
        return nextExecution;
    }

    public void setNextExecution(LocalDateTime nextExecution) {
        this.nextExecution = nextExecution;
    }

    public long getExecutions() {
        return executions;
    }

    public void setExecutions(long executions) {
        this.executions = executions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
