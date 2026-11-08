package com.example.cgpaplatform.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cgpaplatform.entity.subject;
import com.example.cgpaplatform.repository.SubjectRepository;

@Service
public class SgpaService {

    private final SubjectRepository subjectRepository;

    public SgpaService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Double calculateSgpa(Long semesterId) {

        List<subject> subjects =
                subjectRepository.findBySemester_Id(semesterId);

        double totalCredits = 0;
        double totalPoints = 0;

        for (subject s : subjects) {

            if (s.getCredits() != null && s.getGradePoint() != null) {

                totalCredits += s.getCredits();

                totalPoints +=
                        s.getCredits() * s.getGradePoint();
            }
        }

        if (totalCredits == 0) {
            throw new RuntimeException(
                    "No completed subjects found for this semester");
        }

        return totalPoints / totalCredits;
    }
}