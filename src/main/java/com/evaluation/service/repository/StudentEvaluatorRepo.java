package com.evaluation.service.repository;

import com.evaluation.service.models.ChainBlock;
import com.evaluation.service.models.EvaluationCourse;
import com.evaluation.service.models.StudentEvaluator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentEvaluatorRepo extends JpaRepository<StudentEvaluator, Long> {
    List<StudentEvaluator> findByCourse_Id(Long courseId);
    Optional<StudentEvaluator> findByChainBlock(ChainBlock chainBlock);
}
