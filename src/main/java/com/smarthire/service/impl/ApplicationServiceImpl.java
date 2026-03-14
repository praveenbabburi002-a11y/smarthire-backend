package com.smarthire.service.impl;

import com.smarthire.dto.ApplicationResponse;
import com.smarthire.dto.ResumeScreeningResponse;
import com.smarthire.entity.Application;
import com.smarthire.entity.Job;
import com.smarthire.entity.User;
import com.smarthire.repository.ApplicationRepository;
import com.smarthire.repository.JobRepository;
import com.smarthire.repository.UserRepository;
import com.smarthire.service.ApplicationService;
import com.smarthire.service.ResumeScreeningService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final ResumeScreeningService resumeScreeningService;

    public ApplicationServiceImpl(
            ApplicationRepository applicationRepository,
            JobRepository jobRepository,
            UserRepository userRepository,
            ResumeScreeningService resumeScreeningService
    ) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.resumeScreeningService = resumeScreeningService;
    }

    @Override
    public String applyJob(
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
    ) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (job.isClosed()) {
            throw new RuntimeException("This job is closed and no longer accepting applications");
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean alreadyApplied = applicationRepository.existsByJobIdAndUserId(jobId, user.getId());
        if (alreadyApplied) {
            throw new RuntimeException("You have already applied for this job");
        }

        Application application = new Application();
        application.setJob(job);
        application.setUser(user);
        application.setFullName(fullName);
        application.setEmail(userEmail);
        application.setPhone(phone);
        application.setDateOfBirth(dateOfBirth);
        application.setGender(gender);
        application.setDegree(degree);
        application.setSpecialization(specialization);
        application.setCollege(college);
        application.setSkills(skills);
        application.setCoverLetter(coverLetter);
        application.setStatus("PENDING");
        application.setAppliedAt(LocalDateTime.now());
        application.setInterviewDateTime(null);
        application.setAiMatchPercentage(0);
        application.setAiFeedback("AI match not calculated.");
        application.setAiMatchedSkills("");
        application.setAiMissingSkills("");

        if (resume != null && !resume.isEmpty()) {
            try {
                application.setResumeData(resume.getBytes());
                application.setResumeFileName(resume.getOriginalFilename());

                ResumeScreeningResponse aiResult =
                        resumeScreeningService.screenResume(resume, job.getDescription());

                application.setAiMatchPercentage(aiResult.getMatchPercentage());
                application.setAiFeedback(aiResult.getFeedback());
                application.setAiMatchedSkills(
                        aiResult.getMatchedSkills() == null ? "" : String.join(", ", aiResult.getMatchedSkills())
                );
                application.setAiMissingSkills(
                        aiResult.getMissingSkills() == null ? "" : String.join(", ", aiResult.getMissingSkills())
                );
            } catch (IOException e) {
                throw new RuntimeException("Failed to read resume file");
            }
        } else {
            application.setAiMatchPercentage(0);
            application.setAiFeedback("Resume not uploaded, so AI matching could not be calculated.");
            application.setAiMatchedSkills("");
            application.setAiMissingSkills("");
        }

        applicationRepository.save(application);
        return "Application submitted successfully";
    }

    @Override
    public List<ApplicationResponse> userApplications(String userEmail) {
        return applicationRepository.findByEmailOrderByAppliedAtDesc(userEmail)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ApplicationResponse> jobApplicants(Long jobId, String recruiterEmail) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (job.getRecruiter() == null || job.getRecruiter().getEmail() == null ||
                !job.getRecruiter().getEmail().equalsIgnoreCase(recruiterEmail)) {
            throw new RuntimeException("You are not authorized to view applicants for this job");
        }

        return applicationRepository.findByJobIdOrderByAppliedAtDesc(jobId)
                .stream()
                .map(this::mapToResponse)
                .sorted((a, b) -> Integer.compare(
                        b.getAiMatchPercentage() == null ? 0 : b.getAiMatchPercentage(),
                        a.getAiMatchPercentage() == null ? 0 : a.getAiMatchPercentage()
                ))
                .toList();
    }

    @Override
    public String scheduleInterview(Long applicationId, LocalDateTime interviewDateTime, String recruiterEmail) {
        Application application = applicationRepository.findByIdAndJobRecruiterEmail(applicationId, recruiterEmail)
                .orElseThrow(() -> new RuntimeException("Application not found or unauthorized"));

        application.setInterviewDateTime(interviewDateTime);
        application.setStatus("INTERVIEW_SCHEDULED");
        applicationRepository.save(application);

        return "Interview scheduled successfully";
    }

    @Override
    public String rejectApplication(Long applicationId, String recruiterEmail) {
        Application application = applicationRepository.findByIdAndJobRecruiterEmail(applicationId, recruiterEmail)
                .orElseThrow(() -> new RuntimeException("Application not found or unauthorized"));

        application.setStatus("REJECTED");
        application.setInterviewDateTime(null);
        applicationRepository.save(application);

        return "Application rejected successfully";
    }

    @Override
    public String selectApplication(Long applicationId, String recruiterEmail) {
        Application application = applicationRepository.findByIdAndJobRecruiterEmail(applicationId, recruiterEmail)
                .orElseThrow(() -> new RuntimeException("Application not found or unauthorized"));

        application.setStatus("SELECTED");
        applicationRepository.save(application);

        return "Candidate marked as selected";
    }

    @Override
    public String markNotSelected(Long applicationId, String recruiterEmail) {
        Application application = applicationRepository.findByIdAndJobRecruiterEmail(applicationId, recruiterEmail)
                .orElseThrow(() -> new RuntimeException("Application not found or unauthorized"));

        application.setStatus("NOT_SELECTED");
        applicationRepository.save(application);

        return "Candidate marked as not selected";
    }

    @Override
    public Resource downloadResume(Long applicationId, String recruiterEmail) {
        Application application = applicationRepository.findByIdAndJobRecruiterEmail(applicationId, recruiterEmail)
                .orElseThrow(() -> new RuntimeException("Application not found or unauthorized"));

        if (application.getResumeData() == null || application.getResumeData().length == 0) {
            throw new RuntimeException("Resume not found");
        }

        return new ByteArrayResource(application.getResumeData());
    }

    @Override
    public String getResumeFileName(Long applicationId, String recruiterEmail) {
        Application application = applicationRepository.findByIdAndJobRecruiterEmail(applicationId, recruiterEmail)
                .orElseThrow(() -> new RuntimeException("Application not found or unauthorized"));

        return application.getResumeFileName() != null && !application.getResumeFileName().isBlank()
                ? application.getResumeFileName()
                : "resume.pdf";
    }

    private ApplicationResponse mapToResponse(Application application) {
        ApplicationResponse response = new ApplicationResponse();

        response.setId(application.getId());

        if (application.getJob() != null) {
            response.setJobId(application.getJob().getId());
            response.setJobTitle(application.getJob().getTitle());
            response.setCompany(application.getJob().getCompany());
        }

        response.setApplicantName(application.getFullName());
        response.setApplicantEmail(application.getEmail());
        response.setPhone(application.getPhone());
        response.setDateOfBirth(application.getDateOfBirth());
        response.setGender(application.getGender());
        response.setDegree(application.getDegree());
        response.setSpecialization(application.getSpecialization());
        response.setCollege(application.getCollege());
        response.setSkills(application.getSkills());
        response.setCoverLetter(application.getCoverLetter());
        response.setStatus(application.getStatus());
        response.setResumeFileName(application.getResumeFileName());
        response.setAppliedAt(application.getAppliedAt());
        response.setInterviewDateTime(application.getInterviewDateTime());
        response.setAiMatchPercentage(application.getAiMatchPercentage());
        response.setAiFeedback(application.getAiFeedback());
        response.setAiMatchedSkills(application.getAiMatchedSkills());
        response.setAiMissingSkills(application.getAiMissingSkills());

        return response;
    }
}