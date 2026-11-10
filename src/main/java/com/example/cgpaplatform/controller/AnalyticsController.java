
package com.example.cgpaplatform.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cgpaplatform.service.AnalyticsService;

@RestController
@RequestMapping("/api/students")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    // Semester-wise SGPA
    @GetMapping("/{studentId}/analytics/sgpa")
    public Map<Integer, Double> getSemesterWiseSgpa(
            @PathVariable Long studentId) {

        return analyticsService.getSemesterWiseSgpa(studentId);
    }

    // CGPA progression
    @GetMapping("/{studentId}/analytics/progression")
    public Map<Integer, Double> getCgpaProgression(
            @PathVariable Long studentId) {

        return analyticsService.getCgpaProgression(studentId);
    }

    // Strongest and weakest subjects
    @GetMapping("/{studentId}/analytics/subjects")
    public Map<String, Object> getSubjectPerformance(
            @PathVariable Long studentId) {

        return analyticsService.getSubjectPerformance(studentId);
    }
}
