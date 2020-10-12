package com.evaluation.service.controller;

import com.evaluation.service.apimodel.Submission;
import com.evaluation.service.models.EvaluationCourse;
import com.evaluation.service.models.EvaluationQuestion;
import com.evaluation.service.models.StudentEvaluator;
import com.evaluation.service.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {
    @Autowired
    EvaluationService evaluationService;

    @GetMapping(value = "/get-courses")
    public List<EvaluationCourse> getListOfCourses(){
        return evaluationService.getListOfCourses();
    }

    @GetMapping(value = "/get-questions")
    public List<EvaluationQuestion> getQuestions(){
        return evaluationService.getQuestions();
    }

    @PostMapping(value = "/submit")
    public Boolean postSubmission(@RequestBody Submission submission){
        return evaluationService.postSubmission(submission);
    }

    @GetMapping(value = "/get-submissions/{courseId}")
    public List<StudentEvaluator> getSubmissions(@PathVariable(name = "courseId") Long courseId){
        return evaluationService.getSubmissions(courseId);
    }

    @GetMapping(value = "/get-chain")
    public List<String> getChain(){
        return evaluationService.getChain();
    }

    @GetMapping(value = "/check-chain")
    public String checkChain(){
        return evaluationService.checkChainValidity();
    }
}
