
package com.example.cgpaplatform.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "subjects")
public class subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;

    private Integer credits;
    private Double marks;
    private Double gradePoint;
    private String letter;

    @ManyToOne
    @JoinColumn(name = "semester_id", nullable = false)
    private semester semester;

    public subject() {
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }

    public Double getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(Double gradePoint) {
        this.gradePoint = gradePoint;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public semester getSemester() {
        return semester;
    }

    public void setSemester(semester semester) {
        this.semester = semester;
    }
}
