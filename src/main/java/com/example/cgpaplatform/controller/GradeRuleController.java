package com.example.cgpaplatform.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.cgpaplatform.entity.GradeRule;
import com.example.cgpaplatform.repository.GradeRuleRepository;

@RestController
@RequestMapping("/api/grade-rules")
public class GradeRuleController {

    private final GradeRuleRepository gradeRuleRepository;

    public GradeRuleController(GradeRuleRepository gradeRuleRepository) {
        this.gradeRuleRepository = gradeRuleRepository;
    }

    @PostMapping
    public GradeRule addGradeRule(@RequestBody GradeRule gradeRule) {
        return gradeRuleRepository.save(gradeRule);
    }

    @GetMapping
    public List<GradeRule> getAllGradeRules() {
        return gradeRuleRepository.findAll();
    }

    @GetMapping("/{id}")
    public GradeRule getGradeRuleById(@PathVariable Long id) {
        return gradeRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade rule not found"));
    }

    @PutMapping("/{id}")
    public GradeRule updateGradeRule(
            @PathVariable Long id,
            @RequestBody GradeRule updatedRule) {

        GradeRule existing = getGradeRuleById(id);

        existing.setMinMarks(updatedRule.getMinMarks());
        existing.setMaxMarks(updatedRule.getMaxMarks());
        existing.setLetter(updatedRule.getLetter());
        existing.setGradePoint(updatedRule.getGradePoint());
        existing.setActive(updatedRule.getActive());
        existing.setScaleName(updatedRule.getScaleName());

        return gradeRuleRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public String deleteGradeRule(@PathVariable Long id) {
        gradeRuleRepository.delete(getGradeRuleById(id));
        return "Grade rule deleted successfully!";
    }
}