
package com.example.cgpaplatform.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.example.cgpaplatform.service.WhatIfService;

@RestController
@RequestMapping("/api/students")
public class WhatIfController {

    private final WhatIfService whatIfService;

    public WhatIfController(WhatIfService whatIfService) {
        this.whatIfService = whatIfService;
    }

    // Calculate hypothetical CGPA
    @PostMapping("/{studentId}/whatif")
    public Double calculateWhatIf(
            @PathVariable Long studentId,
            @RequestParam Long subjectId,
            @RequestParam Double marks) {

        return whatIfService.calculateWhatIfCgpa(
                studentId, subjectId, marks);
    }

    // Compare multiple hypothetical marks scenarios
    @GetMapping("/{studentId}/whatif/compare")
    public Map<Double, Double> compareWhatIf(
            @PathVariable Long studentId,
            @RequestParam Long subjectId) {

        Map<Double, Double> results = new LinkedHashMap<>();

        double[] scenarios = {70, 80, 90, 100};

        for (double marks : scenarios) {

            Double cgpa = whatIfService.calculateWhatIfCgpa(
                    studentId, subjectId, marks);

            results.put(marks, cgpa);
        }

        return results;
    }

    // Calculate the impact of changing subject marks
    @GetMapping("/{studentId}/whatif/impact")
    public Double calculateSubjectImpact(
            @PathVariable Long studentId,
            @RequestParam Long subjectId,
            @RequestParam Double marks) {

        return whatIfService.calculateSubjectImpact(
                studentId, subjectId, marks);
    }

    // Rank subjects according to their CGPA impact
    @GetMapping("/{studentId}/whatif/sensitivity")
    public Map<Long, Double> calculateSensitivityRanking(
            @PathVariable Long studentId) {

        return whatIfService.calculateSensitivityRanking(studentId);
    }
}
