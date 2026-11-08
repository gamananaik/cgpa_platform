package com.example.cgpaplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.cgpaplatform.entity.GradeRule;

@Repository
public interface GradeRuleRepository extends JpaRepository<GradeRule, Long> {
}