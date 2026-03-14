package com.smarthire.service;

import com.smarthire.dto.AdminDashboardResponse;
import com.smarthire.dto.AdminUserResponse;
import com.smarthire.dto.JobResponse;
import com.smarthire.entity.Job;
import com.smarthire.entity.Role;
import com.smarthire.entity.User;
import com.smarthire.repository.ApplicationRepository;
import com.smarthire.repository.JobRepository;
import com.smarthire.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final JobService jobService;

    public AdminService(
            UserRepository userRepository,
            JobRepository jobRepository,
            ApplicationRepository applicationRepository,
            JobService jobService
    ) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.jobService = jobService;
    }

    public AdminDashboardResponse getDashboard() {
        return new AdminDashboardResponse(
                userRepository.count(),
                userRepository.countByRole(Role.RECRUITER),
                userRepository.countByRole(Role.JOBSEEKER),
                jobRepository.count(),
                jobRepository.countByClosedFalse(),
                applicationRepository.count(),
                applicationRepository.countByStatusIgnoreCase("SELECTED"),
                applicationRepository.countByStatusIgnoreCase("REJECTED")
        );
    }

    public List<AdminUserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new AdminUserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole().name(),
                        user.getJobs() == null ? 0 : user.getJobs().size()
                ))
                .toList();
    }

    public List<JobResponse> getAllJobs() {
        return jobService.getAllJobsForAdmin();
    }

    @Transactional
    public String deleteJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        applicationRepository.deleteByJob(job);
        jobRepository.delete(job);

        return "Job deleted by admin successfully";
    }

    @Transactional
    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.ADMIN) {
            throw new RuntimeException("Admin user cannot be deleted");
        }

        applicationRepository.deleteByUser(user);

        if (user.getJobs() != null && !user.getJobs().isEmpty()) {
            for (Job job : user.getJobs()) {
                applicationRepository.deleteByJob(job);
            }
            jobRepository.deleteAll(user.getJobs());
        }

        userRepository.delete(user);
        return "User deleted by admin successfully";
    }
}