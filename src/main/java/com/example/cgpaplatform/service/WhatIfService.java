
package com.example.cgpaplatform.service;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

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

    // Calculate hypothetical CGPA
    public Double calculateWhatIfCgpa(
            Long studentId,
            Long subjectId,
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

                    totalPoints +=
                            s.getCredits() * rule.getGradePoint();

                    subjectFound = true;

                } else if (s.getMarks() != null
                        && s.getGradePoint() != null) {

                    totalCredits += s.getCredits();

                    totalPoints +=
                            s.getCredits() * s.getGradePoint();
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

        return Math.round(
                (totalPoints / totalCredits) * 100.0) / 100.0;
    }

    // Calculate the impact of changing one subject's marks
    public Double calculateSubjectImpact(
            Long studentId,
            Long subjectId,
            Double hypotheticalMarks) {

        Double currentMarks =
                getCurrentMarks(studentId, subjectId);

        Double currentCgpa = calculateWhatIfCgpa(
                studentId, subjectId, currentMarks);

        Double hypotheticalCgpa = calculateWhatIfCgpa(
                studentId, subjectId, hypotheticalMarks);

        return Math.round(
                (hypotheticalCgpa - currentCgpa) * 100.0) / 100.0;
    }

    // Get the current marks of a subject
    private Double getCurrentMarks(
            Long studentId,
            Long subjectId) {

        List<semester> semesters =
                semesterRepository.findByStudent_Id(studentId);

        for (semester sem : semesters) {

            List<subject> subjects =
                    subjectRepository.findBySemester_Id(sem.getId());

            for (subject s : subjects) {

                if (s.getId().equals(subjectId)) {

                    if (s.getMarks() == null) {
                        throw new IllegalArgumentException(
                                "Subject has no current marks");
                    }

                    return s.getMarks();
                }
            }
        }

        throw new IllegalArgumentException(
                "Subject not found for this student");
    }

    // Rank subjects according to their CGPA impact
    public Map<Long, Double> calculateSensitivityRanking(
            Long studentId) {

        List<semester> semesters =
                semesterRepository.findByStudent_Id(studentId);

        Map<Long, Double> ranking = new LinkedHashMap<>();

        for (semester sem : semesters) {

            List<subject> subjects =
                    subjectRepository.findBySemester_Id(sem.getId());

            for (subject s : subjects) {

                if (s.getMarks() == null || s.getCredits() == null) {
                    continue;
                }

                Double impact = calculateSubjectImpact(
                        studentId,
                        s.getId(),
                        100.0
                );

                ranking.put(s.getId(), impact);
            }
        }

        // Sort subjects by highest absolute CGPA impact
        return ranking.entrySet()
                .stream()
                .sorted((a, b) ->
                        Double.compare(
                                Math.abs(b.getValue()),
                                Math.abs(a.getValue())
                        )
                )
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }
}
