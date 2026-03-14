package com.smarthire.repository;

import com.smarthire.entity.Application;
import com.smarthire.entity.Job;
import com.smarthire.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByJobIdAndUserId(Long jobId, Long userId);

    boolean existsByJobIdAndEmail(Long jobId, String email);

    Optional<Application> findByIdAndJobRecruiterEmail(Long id, String recruiterEmail);

    List<Application> findByEmailOrderByAppliedAtDesc(String email);

    List<Application> findByJobIdOrderByAppliedAtDesc(Long jobId);

    void deleteByJob(Job job);

    void deleteByUser(User user);

    long countByStatusIgnoreCase(String status);
}