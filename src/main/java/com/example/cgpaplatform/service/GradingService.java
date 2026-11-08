package com.example.cgpaplatform.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cgpaplatform.entity.GradeRule;
import com.example.cgpaplatform.repository.GradeRuleRepository;

@Service
public class GradingService {

    private final GradeRuleRepository gradeRuleRepository;

    public GradingService(GradeRuleRepository gradeRuleRepository) {
        this.gradeRuleRepository = gradeRuleRepository;
    }

    public GradeRule calculateGrade(Double marks) {

        if (marks == null || marks < 0 || marks > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100");
        }

        List<GradeRule> rules = gradeRuleRepository.findAll();

        return rules.stream()
                .filter(rule -> Boolean.TRUE.equals(rule.getActive()))
                .filter(rule -> marks >= rule.getMinMarks()
                        && marks <= rule.getMaxMarks())
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "No grading rule found for marks: " + marks));
    }
}