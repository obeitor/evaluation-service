package com.evaluation.service.apimodel;

import com.evaluation.service.models.EvaluationResponse;
import com.evaluation.service.models.StudentEvaluator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Submission {
    private String name;
    private String studentId;
    private Long courseId;
    private String previousHash;
    private List<QuestionResponse> responses = new ArrayList<>();

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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public List<QuestionResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<QuestionResponse> responses) {
        this.responses = responses;
    }

    @JsonIgnore
    public List<EvaluationResponse> getEvaluationResponse(){
        List<EvaluationResponse> evaluationResponses = new ArrayList<>();
        responses.forEach(e->{
            EvaluationResponse response = new EvaluationResponse();
            response.setQuestion(e.question);
            response.setAnswer(e.response);
            evaluationResponses.add(response);
        });
        return evaluationResponses;
    }

    public Submission() {
    }

    public Submission(StudentEvaluator evaluation, String previousHash){
        this.setName(evaluation.getName());
        this.setCourseId(evaluation.getCourse().getId());
        this.setStudentId(evaluation.getStudentId());
        this.setPreviousHash(previousHash);
        evaluation.getSolution().forEach(e->{
            QuestionResponse response = new QuestionResponse();
            response.setQuestion(e.getQuestion());
            response.setResponse(e.getAnswer());
            this.responses.add(response);
        });
    }
    private class QuestionResponse{
        private String question;
        private String response;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }
}
