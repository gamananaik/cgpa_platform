
package com.example.cgpaplatform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cgpaplatform.entity.AcademicYear;

public interface AcademicYearRepository
        extends JpaRepository<AcademicYear, Long> {

    Optional<AcademicYear> findByYearCode(String yearCode);
}
