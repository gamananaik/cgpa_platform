package com.example.cgpaplatform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cgpaplatform.service.PredictionService;

@RestController
@RequestMapping("/api/students")
public class PredictionController {

    private final PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @GetMapping("/{studentId}/predict")
    public Double predictCgpa(
            @PathVariable Long studentId,
            @RequestParam Double target) {

        return predictionService.predictCgpa(studentId, target);
    }
}