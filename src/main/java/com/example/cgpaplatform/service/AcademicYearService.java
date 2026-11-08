
package com.example.cgpaplatform.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cgpaplatform.entity.AcademicYear;
import com.example.cgpaplatform.repository.AcademicYearRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AcademicYearService {

    private final AcademicYearRepository academicYearRepository;

    public AcademicYearService(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }

    public AcademicYear saveAcademicYear(AcademicYear academicYear) {
        return academicYearRepository.save(academicYear);
    }

    public List<AcademicYear> getAllAcademicYears() {
        return academicYearRepository.findAll();
    }

    public AcademicYear getAcademicYearById(Long id) {
        return academicYearRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Academic Year not found with ID: " + id));
    }

    public AcademicYear updateAcademicYear(Long id, AcademicYear updated) {
        AcademicYear existing = getAcademicYearById(id);

        existing.setYearCode(updated.getYearCode());
        existing.setYearName(updated.getYearName());
        existing.setStatus(updated.getStatus());

        return academicYearRepository.save(existing);
    }

    public void deleteAcademicYear(Long id) {
        AcademicYear existing = getAcademicYearById(id);
        academicYearRepository.delete(existing);
    }
}
