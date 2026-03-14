package com.smarthire.config;

import com.smarthire.entity.Application;
import com.smarthire.entity.Job;
import com.smarthire.entity.Role;
import com.smarthire.entity.User;
import com.smarthire.repository.ApplicationRepository;
import com.smarthire.repository.JobRepository;
import com.smarthire.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(
            UserRepository userRepository,
            JobRepository jobRepository,
            ApplicationRepository applicationRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (!userRepository.existsByEmail("admin@smarthire.com")) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@smarthire.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }

        if (userRepository.count() <= 1) {
            User recruiter = new User();
            recruiter.setName("Recruiter One");
            recruiter.setEmail("recruiter@smarthire.com");
            recruiter.setPassword(passwordEncoder.encode("123456"));
            recruiter.setRole(Role.RECRUITER);
            userRepository.save(recruiter);

            User jobSeeker = new User();
            jobSeeker.setName("Job Seeker One");
            jobSeeker.setEmail("jobseeker@smarthire.com");
            jobSeeker.setPassword(passwordEncoder.encode("123456"));
            jobSeeker.setRole(Role.JOBSEEKER);
            userRepository.save(jobSeeker);

            Job job = new Job();
            job.setTitle("Java Developer");
            job.setCompany("SmartHire Tech");
            job.setDescription("Looking for a Java Spring Boot developer");
            job.setLocation("Hyderabad");
            job.setSalary("6 LPA");
            job.setRecruiter(recruiter);
            job.setCreatedAt(LocalDateTime.now());
            job.setClosed(false);
            jobRepository.save(job);

            Application application = new Application();
            application.setJob(job);
            application.setUser(jobSeeker);
            application.setFullName("Job Seeker One");
            application.setEmail("jobseeker@smarthire.com");
            application.setPhone("9876543210");
            application.setGender("Male");
            application.setDegree("B.Tech");
            application.setSpecialization("CSE");
            application.setCollege("Anurag University");
            application.setSkills("Java, Spring Boot, MySQL");
            application.setCoverLetter("I am interested in this opportunity.");
            application.setStatus("PENDING");
            application.setAppliedAt(LocalDateTime.now());

            applicationRepository.save(application);
        }
    }
}