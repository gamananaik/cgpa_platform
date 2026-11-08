package com.example.cgpaplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cgpaplatform.entity.student;

public interface StudentRepository extends JpaRepository<student, Long> {
}