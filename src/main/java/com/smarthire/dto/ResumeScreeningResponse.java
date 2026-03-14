package com.smarthire.dto;

import java.util.List;

public class ResumeScreeningResponse {

    private int matchPercentage;
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private String feedback;
    private String extractedResumeText;

    public ResumeScreeningResponse() {
    }

    public ResumeScreeningResponse(int matchPercentage, List<String> matchedSkills, List<String> missingSkills,
                                   String feedback, String extractedResumeText) {
        this.matchPercentage = matchPercentage;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.feedback = feedback;
        this.extractedResumeText = extractedResumeText;
    }

    public int getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(int matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(List<String> matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getExtractedResumeText() {
        return extractedResumeText;
    }

    public void setExtractedResumeText(String extractedResumeText) {
        this.extractedResumeText = extractedResumeText;
    }
}