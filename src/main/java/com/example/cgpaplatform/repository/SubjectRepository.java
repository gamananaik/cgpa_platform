
package com.example.cgpaplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cgpaplatform.entity.subject;

@Repository
public interface SubjectRepository extends JpaRepository<subject, Long> {

    List<subject> findBySemester_Id(Long semesterId);

}
