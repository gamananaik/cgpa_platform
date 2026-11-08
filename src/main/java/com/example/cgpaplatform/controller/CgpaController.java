package com.example.cgpaplatform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cgpaplatform.service.CgpaService;

@RestController
@RequestMapping("/api/students")
public class CgpaController {

    private final CgpaService cgpaService;

    public CgpaController(CgpaService cgpaService) {
        this.cgpaService = cgpaService;
    }

    @GetMapping("/{studentId}/cgpa")
    public Double calculateCgpa(@PathVariable Long studentId) {
        return cgpaService.calculateCgpa(studentId);
    }
}