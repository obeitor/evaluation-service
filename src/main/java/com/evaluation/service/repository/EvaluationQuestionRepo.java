package com.evaluation.service.repository;

import com.evaluation.service.models.EvaluationQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationQuestionRepo extends JpaRepository<EvaluationQuestion,Long> {
}
