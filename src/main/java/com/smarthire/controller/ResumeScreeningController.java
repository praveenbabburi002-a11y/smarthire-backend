package com.smarthire.controller;

import com.smarthire.dto.ResumeScreeningResponse;
import com.smarthire.service.ResumeScreeningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ResumeScreeningController {

    private final ResumeScreeningService resumeScreeningService;

    public ResumeScreeningController(ResumeScreeningService resumeScreeningService) {
        this.resumeScreeningService = resumeScreeningService;
    }

    @PostMapping("/screen-resume")
    public ResponseEntity<?> screenResume(
            @RequestParam("resume") MultipartFile resume,
            @RequestParam("jobDescription") String jobDescription
    ) {
        try {
            ResumeScreeningResponse response = resumeScreeningService.screenResume(resume, jobDescription);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}