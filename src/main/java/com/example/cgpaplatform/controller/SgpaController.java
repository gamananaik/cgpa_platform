package com.example.cgpaplatform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cgpaplatform.service.SgpaService;

@RestController
@RequestMapping("/api/semesters")
public class SgpaController {

    private final SgpaService sgpaService;

    public SgpaController(SgpaService sgpaService) {
        this.sgpaService = sgpaService;
    }

    @GetMapping("/{semesterId}/sgpa")
    public Double calculateSgpa(@PathVariable Long semesterId) {
        return sgpaService.calculateSgpa(semesterId);
    }
}