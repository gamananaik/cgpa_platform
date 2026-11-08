
package com.example.cgpaplatform.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "academic_years")
public class AcademicYear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String yearCode;

    @Column(nullable = false, length = 100)
    private String yearName;

    @Column(length = 20)
    private String status;

    public AcademicYear() {
    }

    public AcademicYear(String yearCode, String yearName, String status) {
        this.yearCode = yearCode;
        this.yearName = yearName;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYearCode() {
        return yearCode;
    }

    public void setYearCode(String yearCode) {
        this.yearCode = yearCode;
    }

    public String getYearName() {
        return yearName;
    }

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
