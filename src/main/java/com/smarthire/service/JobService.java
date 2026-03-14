package com.smarthire.service;

import com.smarthire.dto.JobRequest;
import com.smarthire.dto.JobResponse;
import com.smarthire.entity.Job;
import com.smarthire.entity.Role;
import com.smarthire.entity.User;
import com.smarthire.repository.ApplicationRepository;
import com.smarthire.repository.JobRepository;
import com.smarthire.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    public JobService(JobRepository jobRepository,
                      UserRepository userRepository,
                      ApplicationRepository applicationRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
    }

    public String createJob(JobRequest request, String recruiterEmail) {
        User recruiter = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));

        if (recruiter.getRole() != Role.RECRUITER) {
            throw new RuntimeException("Only recruiters can post jobs");
        }

        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setDescription(request.getDescription());
        job.setCreatedAt(LocalDateTime.now());
        job.setRecruiter(recruiter);
        job.setClosed(false);

        jobRepository.save(job);

        return "Job posted successfully";
    }

    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .filter(job -> !job.isClosed())
                .map(this::mapToJobResponse)
                .toList();
    }

    public List<JobResponse> getAllJobsForAdmin() {
        return jobRepository.findAll()
                .stream()
                .map(this::mapToJobResponse)
                .toList();
    }

    public List<JobResponse> searchJobs(String keyword) {
        return jobRepository
                .findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(keyword, keyword)
                .stream()
                .filter(job -> !job.isClosed())
                .map(this::mapToJobResponse)
                .toList();
    }

    public List<JobResponse> getJobsByRecruiter(String recruiterEmail) {
        User recruiter = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));

        return jobRepository.findByRecruiter(recruiter)
                .stream()
                .map(this::mapToJobResponse)
                .toList();
    }

    public String updateJob(Long id, JobRequest request, String recruiterEmail) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (job.getRecruiter() == null || !job.getRecruiter().getEmail().equalsIgnoreCase(recruiterEmail)) {
            throw new RuntimeException("You can update only your own jobs");
        }

        if (job.isClosed()) {
            throw new RuntimeException("Closed jobs cannot be edited. Reopen first.");
        }

        job.setTitle(request.getTitle());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setDescription(request.getDescription());

        jobRepository.save(job);

        return "Job updated successfully";
    }

    public String closeJob(Long id, String recruiterEmail) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (job.getRecruiter() == null || !job.getRecruiter().getEmail().equalsIgnoreCase(recruiterEmail)) {
            throw new RuntimeException("You can close only your own jobs");
        }

        job.setClosed(true);
        jobRepository.save(job);

        return "Job closed successfully";
    }

    public String reopenJob(Long id, String recruiterEmail) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (job.getRecruiter() == null || !job.getRecruiter().getEmail().equalsIgnoreCase(recruiterEmail)) {
            throw new RuntimeException("You can reopen only your own jobs");
        }

        job.setClosed(false);
        jobRepository.save(job);

        return "Job reopened successfully";
    }

    @Transactional
    public String deleteJob(Long id, String actorEmail) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        User actor = userRepository.findByEmail(actorEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isOwner = job.getRecruiter() != null && job.getRecruiter().getEmail().equalsIgnoreCase(actorEmail);
        boolean isAdmin = actor.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("You are not allowed to delete this job");
        }

        applicationRepository.deleteByJob(job);
        jobRepository.delete(job);

        return "Job deleted successfully";
    }

    private JobResponse mapToJobResponse(Job job) {
        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getCompany(),
                job.getLocation(),
                job.getSalary(),
                job.getDescription(),
                job.getRecruiter() != null ? job.getRecruiter().getId() : null,
                job.getRecruiter() != null ? job.getRecruiter().getName() : null,
                job.getCreatedAt(),
                job.isClosed()
        );
    }
}