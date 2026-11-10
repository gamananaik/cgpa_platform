
package com.example.cgpaplatform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cgpaplatform.service.CgpaService;

@RestController
@RequestMapping("/api")
public class CgpaController {

    private final CgpaService cgpaService;

    public CgpaController(CgpaService cgpaService) {
        this.cgpaService = cgpaService;
    }

    // Calculate CGPA for a student
    @GetMapping("/students/{studentId}/cgpa")
    public Double calculateCgpa(@PathVariable Long studentId) {
        return cgpaService.calculateCgpa(studentId);
    }

    // Calculate SGPA for a semester
    @GetMapping("/semesters/{semesterId}/sgpa")
    public Double calculateSgpa(@PathVariable Long semesterId) {
        return cgpaService.calculateSgpa(semesterId);
    }
}
