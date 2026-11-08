
package com.example.cgpaplatform.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.cgpaplatform.entity.semester;
import com.example.cgpaplatform.service.semesterService;

@RestController
@RequestMapping("/api/semesters")
public class semesterController {

    private final semesterService semesterService;

    public semesterController(semesterService semesterService) {
        this.semesterService = semesterService;
    }

    @PostMapping
    public semester addSemester(@RequestBody semester semester) {
        return semesterService.saveSemester(semester);
    }

    @GetMapping
    public List<semester> getAllSemesters() {
        return semesterService.getAllSemesters();
    }

    @GetMapping("/{id}")
    public semester getSemesterById(@PathVariable Long id) {
        return semesterService.getSemesterById(id);
    }

    @GetMapping("/student/{studentId}")
    public List<semester> getSemestersByStudentId(
            @PathVariable Long studentId) {
        return semesterService.getSemestersByStudentId(studentId);
    }

    @PutMapping("/{id}")
    public semester updateSemester(
            @PathVariable Long id,
            @RequestBody semester semester) {
        return semesterService.updateSemester(id, semester);
    }

    @DeleteMapping("/{id}")
    public String deleteSemester(@PathVariable Long id) {
        semesterService.deleteSemester(id);
        return "Semester deleted successfully!";
    }
}
