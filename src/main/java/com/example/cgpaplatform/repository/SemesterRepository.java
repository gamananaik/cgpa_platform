
package com.example.cgpaplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cgpaplatform.entity.semester;

@Repository
public interface SemesterRepository extends JpaRepository<semester, Long> {

    List<semester> findByStudent_Id(Long studentId);

}
