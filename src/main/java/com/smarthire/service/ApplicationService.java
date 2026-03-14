package com.smarthire.service;

import com.smarthire.dto.ApplicationResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ApplicationService {

    String applyJob(
            Long jobId,
            String userEmail,
            String fullName,
            String phone,
            LocalDate dateOfBirth,
            String gender,
            String degree,
            String specialization,
            String college,
            String coverLetter,
            MultipartFile resume,
            String skills
    );

    List<ApplicationResponse> userApplications(String userEmail);

    List<ApplicationResponse> jobApplicants(Long jobId, String recruiterEmail);

    String scheduleInterview(Long applicationId, LocalDateTime interviewDateTime, String recruiterEmail);

    String rejectApplication(Long applicationId, String recruiterEmail);

    String selectApplication(Long applicationId, String recruiterEmail);

    String markNotSelected(Long applicationId, String recruiterEmail);

    Resource downloadResume(Long applicationId, String recruiterEmail);

    String getResumeFileName(Long applicationId, String recruiterEmail);
}