package com.evaluation.service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softobt.jpa.helpers.converters.DateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student_evaluator")
public class StudentEvaluator {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String studentId;
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime submitDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_evaluated_id", referencedColumnName = "id")
    private EvaluationCourse course;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chain_block_id",referencedColumnName = "id")
    private ChainBlock chainBlock;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "response_id", referencedColumnName = "id")
    private List<EvaluationResponse> solution = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public EvaluationCourse getCourse() {
        return course;
    }

    public void setCourse(EvaluationCourse course) {
        this.course = course;
    }

    public LocalDateTime getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(LocalDateTime submitDate) {
        this.submitDate = submitDate;
    }

    public ChainBlock getChainBlock() {
        return chainBlock;
    }

    public void setChainBlock(ChainBlock chainBlock) {
        this.chainBlock = chainBlock;
    }

    public List<EvaluationResponse> getSolution() {
        return solution;
    }

    public void setSolution(List<EvaluationResponse> solution) {
        this.solution = solution;
    }
}
