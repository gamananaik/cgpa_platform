package com.example.cgpaplatform.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cgpaplatform.entity.semester;
import com.example.cgpaplatform.entity.subject;
import com.example.cgpaplatform.repository.SemesterRepository;
import com.example.cgpaplatform.repository.SubjectRepository;

@Service
public class WhatIfService {

    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;
    private final GradingService gradingService;

    public WhatIfService(
            SemesterRepository semesterRepository,
            SubjectRepository subjectRepository,
            GradingService gradingService) {
        this.semesterRepository = semesterRepository;
        this.subjectRepository = subjectRepository;
        this.gradingService = gradingService;
    }

    public Double calculateWhatIfCgpa(Long studentId, Long subjectId,
            Double hypotheticalMarks) {

        var rule = gradingService.calculateGrade(hypotheticalMarks);

        List<semester> semesters =
                semesterRepository.findByStudent_Id(studentId);

        double totalCredits = 0;
        double totalPoints = 0;
        boolean subjectFound = false;

        for (semester sem : semesters) {
            List<subject> subjects =
                    subjectRepository.findBySemester_Id(sem.getId());

            for (subject s : subjects) {
                if (s.getCredits() == null) {
                    continue;
                }

                if (s.getId().equals(subjectId)) {
                    totalCredits += s.getCredits();
                    totalPoints += s.getCredits() * rule.getGradePoint();
                    subjectFound = true;
                } else if (s.getGradePoint() != null) {
                    totalCredits += s.getCredits();
                    totalPoints += s.getCredits() * s.getGradePoint();
                }
            }
        }

        if (!subjectFound) {
            throw new IllegalArgumentException(
                    "Subject not found for this student");
        }

        if (totalCredits == 0) {
            throw new IllegalArgumentException(
                    "No credits available for CGPA calculation");
        }

        return Math.round((totalPoints / totalCredits) * 100.0) / 100.0;
    }
}