package com.smarthire.dto;

import java.time.LocalDateTime;

public class JobResponse {

    private Long id;
    private String title;
    private String company;
    private String location;
    private String salary;
    private String description;
    private Long recruiterId;
    private String recruiterName;
    private LocalDateTime createdAt;
    private boolean closed;

    public JobResponse() {
    }

    public JobResponse(
            Long id,
            String title,
            String company,
            String location,
            String salary,
            String description,
            Long recruiterId,
            String recruiterName,
            LocalDateTime createdAt,
            boolean closed
    ) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.salary = salary;
        this.description = description;
        this.recruiterId = recruiterId;
        this.recruiterName = recruiterName;
        this.createdAt = createdAt;
        this.closed = closed;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getSalary() {
        return salary;
    }

    public String getDescription() {
        return description;
    }

    public Long getRecruiterId() {
        return recruiterId;
    }

    public String getRecruiterName() {
        return recruiterName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRecruiterId(Long recruiterId) {
        this.recruiterId = recruiterId;
    }

    public void setRecruiterName(String recruiterName) {
        this.recruiterName = recruiterName;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}