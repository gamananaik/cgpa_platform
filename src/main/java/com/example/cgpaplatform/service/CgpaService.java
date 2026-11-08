package com.example.cgpaplatform.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cgpaplatform.entity.semester;
import com.example.cgpaplatform.entity.subject;
import com.example.cgpaplatform.repository.SemesterRepository;
import com.example.cgpaplatform.repository.SubjectRepository;

@Service
public class CgpaService {

    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;

    public CgpaService(
            SemesterRepository semesterRepository,
            SubjectRepository subjectRepository) {
        this.semesterRepository = semesterRepository;
        this.subjectRepository = subjectRepository;
    }

    public Double calculateCgpa(Long studentId) {

        List<semester> semesters =
                semesterRepository.findByStudent_Id(studentId);

        double totalCredits = 0;
        double totalPoints = 0;

        for (semester sem : semesters) {

            List<subject> subjects =
                    subjectRepository.findBySemester_Id(sem.getId());

            for (subject s : subjects) {

                if (s.getCredits() != null
                        && s.getGradePoint() != null) {

                    totalCredits += s.getCredits();

                    totalPoints +=
                            s.getCredits() * s.getGradePoint();
                }
            }
        }

        if (totalCredits == 0) {
            throw new RuntimeException(
                    "No completed subjects found for this student");
        }

        return totalPoints / totalCredits;
    }
}