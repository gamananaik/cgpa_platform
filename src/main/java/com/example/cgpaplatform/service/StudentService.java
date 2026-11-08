package com.example.cgpaplatform.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cgpaplatform.entity.student;
import com.example.cgpaplatform.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public student saveStudent(student student) {
        return studentRepository.save(student);
    }

    public List<student> getAllStudents() {
        return studentRepository.findAll();
    }

    public student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Student not found with ID: " + id));
    }

    public student updateStudent(Long id, student updatedStudent) {
        student existingStudent = getStudentById(id);

        existingStudent.setStudentId(updatedStudent.getStudentId());
        existingStudent.setFullName(updatedStudent.getFullName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setDepartment(updatedStudent.getDepartment());
        existingStudent.setCurrentSemester(updatedStudent.getCurrentSemester());

        return studentRepository.save(existingStudent);
    }

    public void deleteStudent(Long id) {
        student student = getStudentById(id);
        studentRepository.delete(student);
    }
}