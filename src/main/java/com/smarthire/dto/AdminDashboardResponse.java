package com.smarthire.dto;

public class AdminDashboardResponse {

    private long totalUsers;
    private long totalRecruiters;
    private long totalJobSeekers;
    private long totalJobs;
    private long openJobs;
    private long totalApplications;
    private long selectedApplications;
    private long rejectedApplications;

    public AdminDashboardResponse() {
    }

    public AdminDashboardResponse(
            long totalUsers,
            long totalRecruiters,
            long totalJobSeekers,
            long totalJobs,
            long openJobs,
            long totalApplications,
            long selectedApplications,
            long rejectedApplications
    ) {
        this.totalUsers = totalUsers;
        this.totalRecruiters = totalRecruiters;
        this.totalJobSeekers = totalJobSeekers;
        this.totalJobs = totalJobs;
        this.openJobs = openJobs;
        this.totalApplications = totalApplications;
        this.selectedApplications = selectedApplications;
        this.rejectedApplications = rejectedApplications;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public long getTotalRecruiters() {
        return totalRecruiters;
    }

    public long getTotalJobSeekers() {
        return totalJobSeekers;
    }

    public long getTotalJobs() {
        return totalJobs;
    }

    public long getOpenJobs() {
        return openJobs;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public long getSelectedApplications() {
        return selectedApplications;
    }

    public long getRejectedApplications() {
        return rejectedApplications;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public void setTotalRecruiters(long totalRecruiters) {
        this.totalRecruiters = totalRecruiters;
    }

    public void setTotalJobSeekers(long totalJobSeekers) {
        this.totalJobSeekers = totalJobSeekers;
    }

    public void setTotalJobs(long totalJobs) {
        this.totalJobs = totalJobs;
    }

    public void setOpenJobs(long openJobs) {
        this.openJobs = openJobs;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public void setSelectedApplications(long selectedApplications) {
        this.selectedApplications = selectedApplications;
    }

    public void setRejectedApplications(long rejectedApplications) {
        this.rejectedApplications = rejectedApplications;
    }
}