package com.smarthire.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ApplicationResponse {

    private Long id;
    private Long jobId;
    private String jobTitle;
    private String company;
    private String applicantName;
    private String applicantEmail;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
    private String degree;
    private String specialization;
    private String college;
    private String skills;
    private String coverLetter;
    private String status;
    private String resumeFileName;
    private LocalDateTime appliedAt;
    private LocalDateTime interviewDateTime;
    private Integer aiMatchPercentage;
    private String aiFeedback;
    private String aiMatchedSkills;
    private String aiMissingSkills;

    public ApplicationResponse() {
    }

    public ApplicationResponse(
            Long id,
            Long jobId,
            String jobTitle,
            String company,
            String applicantName,
            String applicantEmail,
            String phone,
            LocalDate dateOfBirth,
            String gender,
            String degree,
            String specialization,
            String college,
            String skills,
            String coverLetter,
            String status,
            String resumeFileName,
            LocalDateTime appliedAt,
            LocalDateTime interviewDateTime,
            Integer aiMatchPercentage,
            String aiFeedback,
            String aiMatchedSkills,
            String aiMissingSkills
    ) {
        this.id = id;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.company = company;
        this.applicantName = applicantName;
        this.applicantEmail = applicantEmail;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.degree = degree;
        this.specialization = specialization;
        this.college = college;
        this.skills = skills;
        this.coverLetter = coverLetter;
        this.status = status;
        this.resumeFileName = resumeFileName;
        this.appliedAt = appliedAt;
        this.interviewDateTime = interviewDateTime;
        this.aiMatchPercentage = aiMatchPercentage;
        this.aiFeedback = aiFeedback;
        this.aiMatchedSkills = aiMatchedSkills;
        this.aiMissingSkills = aiMissingSkills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResumeFileName() {
        return resumeFileName;
    }

    public void setResumeFileName(String resumeFileName) {
        this.resumeFileName = resumeFileName;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    public LocalDateTime getInterviewDateTime() {
        return interviewDateTime;
    }

    public void setInterviewDateTime(LocalDateTime interviewDateTime) {
        this.interviewDateTime = interviewDateTime;
    }

    public Integer getAiMatchPercentage() {
        return aiMatchPercentage;
    }

    public void setAiMatchPercentage(Integer aiMatchPercentage) {
        this.aiMatchPercentage = aiMatchPercentage;
    }

    public String getAiFeedback() {
        return aiFeedback;
    }

    public void setAiFeedback(String aiFeedback) {
        this.aiFeedback = aiFeedback;
    }

    public String getAiMatchedSkills() {
        return aiMatchedSkills;
    }

    public void setAiMatchedSkills(String aiMatchedSkills) {
        this.aiMatchedSkills = aiMatchedSkills;
    }

    public String getAiMissingSkills() {
        return aiMissingSkills;
    }

    public void setAiMissingSkills(String aiMissingSkills) {
        this.aiMissingSkills = aiMissingSkills;
    }
}