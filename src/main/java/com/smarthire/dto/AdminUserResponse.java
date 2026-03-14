package com.smarthire.dto;

public class AdminUserResponse {

    private Long id;
    private String name;
    private String email;
    private String role;
    private int jobsCount;

    public AdminUserResponse() {
    }

    public AdminUserResponse(Long id, String name, String email, String role, int jobsCount) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.jobsCount = jobsCount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public int getJobsCount() {
        return jobsCount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setJobsCount(int jobsCount) {
        this.jobsCount = jobsCount;
    }
}