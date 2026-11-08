
package com.example.cgpaplatform.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.cgpaplatform.entity.subject;
import com.example.cgpaplatform.service.subjectService;

@RestController
@RequestMapping("/api/subjects")
public class subjectController {

    private final subjectService subjectService;

    public subjectController(subjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public subject addsubject(@RequestBody subject subject) {
        return subjectService.saveSubject(subject);
    }

    @GetMapping
    public List<subject> getAllsubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public subject getsubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id);
    }

    @GetMapping("/semester/{semesterId}")
    public List<subject> getsubjectsBySemesterId(
            @PathVariable Long semesterId) {
        return subjectService.getSubjectsBySemesterId(semesterId);
    }

    @PutMapping("/{id}")
    public subject updatesubject(
            @PathVariable Long id,
            @RequestBody subject subject) {
        return subjectService.updateSubject(id, subject);
    }

    @DeleteMapping("/{id}")
    public String deletesubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return "subject deleted successfully!";
    }
}
