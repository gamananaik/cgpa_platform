package com.example.cgpaplatform.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cgpaplatform.entity.subject;
import com.example.cgpaplatform.entity.GradeRule;
import com.example.cgpaplatform.repository.SubjectRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class subjectService {

    private final SubjectRepository subjectRepository;
    private final GradingService gradingService;

    public subjectService(
            SubjectRepository subjectRepository,
            GradingService gradingService) {
        this.subjectRepository = subjectRepository;
        this.gradingService = gradingService;
    }

    public subject saveSubject(subject subject) {
        if (subject.getMarks() != null) {
            GradeRule rule = gradingService.calculateGrade(subject.getMarks());
            subject.setGradePoint(rule.getGradePoint());
            subject.setLetter(rule.getLetter());
        }
        return subjectRepository.save(subject);
    }

    public List<subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() ->
                    new EntityNotFoundException("Subject not found with ID: " + id));
    }

    public List<subject> getSubjectsBySemesterId(Long semesterId) {
        return subjectRepository.findBySemester_Id(semesterId);
    }

    public subject updateSubject(Long id, subject updatedSubject) {
        subject existing = getSubjectById(id);

        existing.setCode(updatedSubject.getCode());
        existing.setName(updatedSubject.getName());
        existing.setCredits(updatedSubject.getCredits());
        existing.setMarks(updatedSubject.getMarks());
        existing.setSemester(updatedSubject.getSemester());

        if (updatedSubject.getMarks() != null) {
            GradeRule rule = gradingService.calculateGrade(updatedSubject.getMarks());
            existing.setGradePoint(rule.getGradePoint());
            existing.setLetter(rule.getLetter());
        } else {
            existing.setGradePoint(null);
            existing.setLetter(null);
        }

        return subjectRepository.save(existing);
    }

    public void deleteSubject(Long id) {
        subject existing = getSubjectById(id);
        subjectRepository.delete(existing);
    }
}