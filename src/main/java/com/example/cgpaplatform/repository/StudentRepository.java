package com.example.cgpaplatform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cgpaplatform.entity.student;

public interface StudentRepository extends JpaRepository<student, Long> {

    Optional<student> findByStudentId(String studentId);

    Optional<student> findByUserUsername(String username);

}