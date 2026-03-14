package com.smarthire.service;

import com.smarthire.dto.ResumeScreeningResponse;
import com.smarthire.util.SkillLibrary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeScreeningService {

    private final FileTextExtractorService fileTextExtractorService;

    public ResumeScreeningService(FileTextExtractorService fileTextExtractorService) {
        this.fileTextExtractorService = fileTextExtractorService;
    }

    public ResumeScreeningResponse screenResume(MultipartFile resumeFile, String jobDescription) throws IOException {
        String resumeText = fileTextExtractorService.extractText(resumeFile).toLowerCase();
        String jdText = jobDescription.toLowerCase();

        List<String> matchedSkills = new ArrayList<>();
        List<String> missingSkills = new ArrayList<>();

        List<String> skillsToCheck = new ArrayList<>();

        for (String skill : SkillLibrary.COMMON_SKILLS) {
            if (jdText.contains(skill)) {
                skillsToCheck.add(skill);
            }
        }

        if (skillsToCheck.isEmpty()) {
            skillsToCheck.addAll(SkillLibrary.COMMON_SKILLS);
        }

        for (String skill : skillsToCheck) {
            if (resumeText.contains(skill)) {
                matchedSkills.add(skill);
            } else {
                missingSkills.add(skill);
            }
        }

        int totalSkills = skillsToCheck.size();
        int matchPercentage = totalSkills == 0 ? 0 : (matchedSkills.size() * 100) / totalSkills;

        String feedback = generateFeedback(matchPercentage, matchedSkills, missingSkills);

        return new ResumeScreeningResponse(
                matchPercentage,
                matchedSkills,
                missingSkills,
                feedback,
                resumeText
        );
    }

    private String generateFeedback(int matchPercentage, List<String> matchedSkills, List<String> missingSkills) {
        if (matchPercentage >= 80) {
            return "Excellent resume match. Your resume matches most of the required skills.";
        } else if (matchPercentage >= 60) {
            return "Good resume match. Add a few more missing skills to improve your chances.";
        } else if (matchPercentage >= 40) {
            return "Average match. Your resume needs improvement for this role.";
        } else {
            return "Low match. Update your resume with relevant skills, projects, and experience.";
        }
    }
}