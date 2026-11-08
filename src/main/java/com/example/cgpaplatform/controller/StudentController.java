package com.example.cgpaplatform.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.cgpaplatform.entity.student;
import com.example.cgpaplatform.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public student addStudent(@RequestBody student student) {
        return studentService.saveStudent(student);
    }

    @GetMapping
    public List<student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    public student updateStudent(
            @PathVariable Long id,
            @RequestBody student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "Student deleted successfully!";
    }
}