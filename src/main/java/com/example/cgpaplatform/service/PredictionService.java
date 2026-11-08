package com.example.cgpaplatform.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cgpaplatform.entity.semester;
import com.example.cgpaplatform.entity.subject;
import com.example.cgpaplatform.repository.SemesterRepository;
import com.example.cgpaplatform.repository.SubjectRepository;

@Service
public class PredictionService {

    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;

    public PredictionService(
            SemesterRepository semesterRepository,
            SubjectRepository subjectRepository) {
        this.semesterRepository = semesterRepository;
        this.subjectRepository = subjectRepository;
    }

    public Double predictCgpa(Long studentId, Double targetCgpa) {

        if (targetCgpa == null || targetCgpa < 0 || targetCgpa > 10) {
            throw new IllegalArgumentException(
                    "Target CGPA must be between 0 and 10");
        }

        List<semester> semesters =
                semesterRepository.findByStudent_Id(studentId);

        double completedCredits = 0;
        double completedPoints = 0;
        double remainingCredits = 0;

        for (semester sem : semesters) {

            List<subject> subjects =
                    subjectRepository.findBySemester_Id(sem.getId());

            for (subject s : subjects) {

                if (s.getCredits() == null) {
                    continue;
                }

                if (s.getMarks() != null && s.getGradePoint() != null) {
                    completedCredits += s.getCredits();
                    completedPoints +=
                            s.getCredits() * s.getGradePoint();
                } else if (s.getMarks() == null) {
                    remainingCredits += s.getCredits();
                }
            }
        }

        if (remainingCredits == 0) {
            throw new RuntimeException(
                    "No remaining planned subjects available");
        }

        double requiredGradePoint =
                (targetCgpa * (completedCredits + remainingCredits)
                        - completedPoints) / remainingCredits;

        if (requiredGradePoint > 10) {
            throw new RuntimeException(
                    "Target CGPA is not achievable with the remaining subjects");
        }

        if (requiredGradePoint < 0) {
            requiredGradePoint = 0;
        }

        return Math.round(requiredGradePoint * 100.0) / 100.0;
    }
}