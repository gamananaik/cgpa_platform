package com.example.cgpaplatform.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.cgpaplatform.entity.AcademicYear;
import com.example.cgpaplatform.service.AcademicYearService;

@RestController
@RequestMapping("/api/academic-years")
public class AcademicYearController {

    private final AcademicYearService academicYearService;

    public AcademicYearController(AcademicYearService academicYearService) {
        this.academicYearService = academicYearService;
    }

    @PostMapping
    public AcademicYear addAcademicYear(
            @RequestBody AcademicYear academicYear) {
        return academicYearService.saveAcademicYear(academicYear);
    }

    @GetMapping
    public List<AcademicYear> getAllAcademicYears() {
        return academicYearService.getAllAcademicYears();
    }

    @GetMapping("/{id}")
    public AcademicYear getAcademicYearById(@PathVariable Long id) {
        return academicYearService.getAcademicYearById(id);
    }

    @PutMapping("/{id}")
    public AcademicYear updateAcademicYear(
            @PathVariable Long id,
            @RequestBody AcademicYear academicYear) {
        return academicYearService.updateAcademicYear(id, academicYear);
    }

    @DeleteMapping("/{id}")
    public String deleteAcademicYear(@PathVariable Long id) {
        academicYearService.deleteAcademicYear(id);
        return "Academic Year deleted successfully!";
    }
}