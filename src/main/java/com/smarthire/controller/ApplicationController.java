package com.smarthire.controller;

import com.smarthire.dto.ApplicationResponse;
import com.smarthire.service.ApplicationService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping(value = "/apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> applyJob(
            @RequestParam Long jobId,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String dateOfBirth,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String degree,
            @RequestParam(required = false) String qualification,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String college,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String coverLetter,
            @RequestParam(required = false) MultipartFile resume,
            @RequestParam(required = false) String skills,
            Authentication authentication
    ) {
        LocalDate dob = null;
        if (dateOfBirth != null && !dateOfBirth.isBlank()) {
            dob = LocalDate.parse(dateOfBirth);
        }

        String userEmail = authentication.getName();
        String finalDegree = (degree != null && !degree.isBlank()) ? degree : qualification;
        String finalCollege = (college != null && !college.isBlank()) ? college : address;

        if (fullName == null || fullName.isBlank()) {
            fullName = "Job Seeker";
        }

        if (phone == null || phone.isBlank()) {
            phone = "Not Provided";
        }

        String message = applicationService.applyJob(
                jobId,
                userEmail,
                fullName,
                phone,
                dob,
                gender,
                finalDegree,
                specialization,
                finalCollege,
                coverLetter,
                resume,
                skills
        );

        return ResponseEntity.ok(Map.of("message", message));
    }

    @PostMapping(value = "/apply/{jobId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> applyJobOld(
            @PathVariable Long jobId,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String dateOfBirth,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String degree,
            @RequestParam(required = false) String qualification,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String college,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String coverLetter,
            @RequestParam(required = false) MultipartFile resume,
            @RequestParam(required = false) String skills,
            Authentication authentication
    ) {
        LocalDate dob = null;
        if (dateOfBirth != null && !dateOfBirth.isBlank()) {
            dob = LocalDate.parse(dateOfBirth);
        }

        String userEmail = authentication.getName();
        String finalDegree = (degree != null && !degree.isBlank()) ? degree : qualification;
        String finalCollege = (college != null && !college.isBlank()) ? college : address;

        if (fullName == null || fullName.isBlank()) {
            fullName = "Job Seeker";
        }

        if (phone == null || phone.isBlank()) {
            phone = "Not Provided";
        }

        String message = applicationService.applyJob(
                jobId,
                userEmail,
                fullName,
                phone,
                dob,
                gender,
                finalDegree,
                specialization,
                finalCollege,
                coverLetter,
                resume,
                skills
        );

        return ResponseEntity.ok(Map.of("message", message));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ApplicationResponse>> myApplications(Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(applicationService.userApplications(userEmail));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponse>> jobApplicants(
            @PathVariable Long jobId,
            Authentication authentication
    ) {
        String recruiterEmail = authentication.getName();
        return ResponseEntity.ok(applicationService.jobApplicants(jobId, recruiterEmail));
    }

    @PutMapping("/{applicationId}/schedule-interview")
    public ResponseEntity<?> scheduleInterview(
            @PathVariable Long applicationId,
            @RequestBody Map<String, String> body,
            Authentication authentication
    ) {
        String recruiterEmail = authentication.getName();
        String interviewDateTime = body.get("interviewDateTime");

        if (interviewDateTime == null || interviewDateTime.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Interview date and time are required"));
        }

        String message = applicationService.scheduleInterview(
                applicationId,
                LocalDateTime.parse(interviewDateTime),
                recruiterEmail
        );

        return ResponseEntity.ok(Map.of("message", message));
    }

    @PutMapping("/{applicationId}/reject")
    public ResponseEntity<?> rejectApplication(
            @PathVariable Long applicationId,
            Authentication authentication
    ) {
        String recruiterEmail = authentication.getName();
        String message = applicationService.rejectApplication(applicationId, recruiterEmail);
        return ResponseEntity.ok(Map.of("message", message));
    }

    @PutMapping("/{applicationId}/select")
    public ResponseEntity<?> selectApplication(
            @PathVariable Long applicationId,
            Authentication authentication
    ) {
        String recruiterEmail = authentication.getName();
        String message = applicationService.selectApplication(applicationId, recruiterEmail);
        return ResponseEntity.ok(Map.of("message", message));
    }

    @PutMapping("/{applicationId}/not-selected")
    public ResponseEntity<?> markNotSelected(
            @PathVariable Long applicationId,
            Authentication authentication
    ) {
        String recruiterEmail = authentication.getName();
        String message = applicationService.markNotSelected(applicationId, recruiterEmail);
        return ResponseEntity.ok(Map.of("message", message));
    }

    @GetMapping("/{applicationId}/resume")
    public ResponseEntity<Resource> downloadResume(
            @PathVariable Long applicationId,
            Authentication authentication
    ) {
        String recruiterEmail = authentication.getName();
        Resource resource = applicationService.downloadResume(applicationId, recruiterEmail);
        String fileName = applicationService.getResumeFileName(applicationId, recruiterEmail);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}