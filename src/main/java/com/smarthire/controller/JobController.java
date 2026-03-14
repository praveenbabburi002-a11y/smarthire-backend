package com.smarthire.controller;

import com.smarthire.dto.JobRequest;
import com.smarthire.dto.JobResponse;
import com.smarthire.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody JobRequest request, Authentication authentication) {
        String recruiterEmail = authentication.getName();
        return ResponseEntity.ok(jobService.createJob(request, recruiterEmail));
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<JobResponse>> searchJobs(@PathVariable String keyword) {
        return ResponseEntity.ok(jobService.searchJobs(keyword));
    }

    @GetMapping("/my-jobs")
    public ResponseEntity<List<JobResponse>> getMyJobs(Authentication authentication) {
        String recruiterEmail = authentication.getName();
        return ResponseEntity.ok(jobService.getJobsByRecruiter(recruiterEmail));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id,
                                            @RequestBody JobRequest request,
                                            Authentication authentication) {
        String recruiterEmail = authentication.getName();
        return ResponseEntity.ok(jobService.updateJob(id, request, recruiterEmail));
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<String> closeJob(@PathVariable Long id, Authentication authentication) {
        String recruiterEmail = authentication.getName();
        return ResponseEntity.ok(jobService.closeJob(id, recruiterEmail));
    }

    @PutMapping("/{id}/reopen")
    public ResponseEntity<String> reopenJob(@PathVariable Long id, Authentication authentication) {
        String recruiterEmail = authentication.getName();
        return ResponseEntity.ok(jobService.reopenJob(id, recruiterEmail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id,
                                            Authentication authentication) {
        String actorEmail = authentication.getName();
        return ResponseEntity.ok(jobService.deleteJob(id, actorEmail));
    }
}