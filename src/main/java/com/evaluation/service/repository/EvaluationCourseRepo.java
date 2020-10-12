package com.evaluation.service.repository;

import com.evaluation.service.models.EvaluationCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationCourseRepo extends JpaRepository<EvaluationCourse, Long> {

}
