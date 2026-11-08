package com.example.cgpaplatform.controller;

import org.springframework.web.bind.annotation.*;

import com.example.cgpaplatform.service.WhatIfService;

@RestController
@RequestMapping("/api/students")
public class WhatIfController {

    private final WhatIfService whatIfService;

    public WhatIfController(WhatIfService whatIfService) {
        this.whatIfService = whatIfService;
    }

    @PostMapping("/{studentId}/whatif")
    public Double calculateWhatIf(
            @PathVariable Long studentId,
            @RequestParam Long subjectId,
            @RequestParam Double marks) {

        return whatIfService.calculateWhatIfCgpa(
                studentId, subjectId, marks);
    }
}