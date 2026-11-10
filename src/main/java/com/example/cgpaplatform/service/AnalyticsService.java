
package com.example.cgpaplatform.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.cgpaplatform.entity.semester;
import com.example.cgpaplatform.entity.subject;
import com.example.cgpaplatform.repository.SemesterRepository;
import com.example.cgpaplatform.repository.SubjectRepository;

@Service
public class AnalyticsService {

    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;

    public AnalyticsService(
            SemesterRepository semesterRepository,
            SubjectRepository subjectRepository) {

        this.semesterRepository = semesterRepository;
        this.subjectRepository = subjectRepository;
    }

    // 1. Semester-wise SGPA
    public Map<Integer, Double> getSemesterWiseSgpa(Long studentId) {

        List<semester> semesters =
                semesterRepository.findByStudent_Id(studentId);

        Map<Integer, Double> result = new LinkedHashMap<>();

        for (semester sem : semesters) {

            List<subject> subjects =
                    subjectRepository.findBySemester_Id(sem.getId());

            double totalCredits = 0;
            double totalPoints = 0;

            for (subject s : subjects) {

                if (s.getMarks() != null
                        && s.getCredits() != null
                        && s.getGradePoint() != null) {

                    totalCredits += s.getCredits();
                    totalPoints += s.getCredits() * s.getGradePoint();
                }
            }

            if (totalCredits > 0) {

                double sgpa = totalPoints / totalCredits;

                sgpa = Math.round(sgpa * 100.0) / 100.0;

                result.put(sem.getSemesterNumber(), sgpa);
            }
        }

        return result;
    }

    // 2. CGPA progression
    public Map<Integer, Double> getCgpaProgression(Long studentId) {

        List<semester> semesters =
                semesterRepository.findByStudent_Id(studentId);

        Map<Integer, Double> result = new LinkedHashMap<>();

        double totalCredits = 0;
        double totalPoints = 0;

        for (semester sem : semesters) {

            List<subject> subjects =
                    subjectRepository.findBySemester_Id(sem.getId());

            for (subject s : subjects) {

                if (s.getMarks() != null
                        && s.getCredits() != null
                        && s.getGradePoint() != null) {

                    totalCredits += s.getCredits();
                    totalPoints += s.getCredits() * s.getGradePoint();
                }
            }

            if (totalCredits > 0) {

                double cgpa = totalPoints / totalCredits;

                cgpa = Math.round(cgpa * 100.0) / 100.0;

                result.put(sem.getSemesterNumber(), cgpa);
            }
        }

        return result;
    }

    // 3. Strongest and weakest subjects
    public Map<String, Object> getSubjectPerformance(Long studentId) {

        List<semester> semesters =
                semesterRepository.findByStudent_Id(studentId);

        List<subject> completedSubjects = new ArrayList<>();

        for (semester sem : semesters) {

            List<subject> subjects =
                    subjectRepository.findBySemester_Id(sem.getId());

            for (subject s : subjects) {

                if (s.getMarks() != null
                        && s.getGradePoint() != null) {

                    completedSubjects.add(s);
                }
            }
        }

        Map<String, Object> result = new LinkedHashMap<>();

        if (completedSubjects.isEmpty()) {

            result.put("message", "No completed subjects found");

            return result;
        }

        subject strongest = completedSubjects.stream()
                .max((a, b) -> Double.compare(
                        a.getGradePoint(), b.getGradePoint()))
                .get();

        subject weakest = completedSubjects.stream()
                .min((a, b) -> Double.compare(
                        a.getGradePoint(), b.getGradePoint()))
                .get();

        result.put("strongestSubject", strongest.getName());
        result.put("strongestGradePoint", strongest.getGradePoint());

        result.put("weakestSubject", weakest.getName());
        result.put("weakestGradePoint", weakest.getGradePoint());

        return result;
    }
}
