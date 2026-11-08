
package com.example.cgpaplatform.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cgpaplatform.entity.semester;
import com.example.cgpaplatform.repository.SemesterRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class semesterService {

    private final SemesterRepository semesterRepository;

    public semesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    public semester saveSemester(semester semester) {
        return semesterRepository.save(semester);
    }

    public List<semester> getAllSemesters() {
        return semesterRepository.findAll();
    }

    public semester getSemesterById(Long id) {
        return semesterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Semester not found with ID: " + id));
    }

    public List<semester> getSemestersByStudentId(Long studentId) {
        return semesterRepository.findByStudent_Id(studentId);
    }

    public semester updateSemester(Long id, semester updatedSemester) {
        semester existing = getSemesterById(id);

        existing.setSemesterNumber(updatedSemester.getSemesterNumber());
        existing.setSemesterName(updatedSemester.getSemesterName());
        existing.setStatus(updatedSemester.getStatus());
        existing.setStudent(updatedSemester.getStudent());
        existing.setAcademicYear(updatedSemester.getAcademicYear());

        return semesterRepository.save(existing);
    }

    public void deleteSemester(Long id) {
        semester existing = getSemesterById(id);
        semesterRepository.delete(existing);
    }
}
