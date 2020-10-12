package com.evaluation.service.services;

import com.evaluation.service.apimodel.Submission;
import com.evaluation.service.models.*;
import com.evaluation.service.repository.ChainBlockRepo;
import com.evaluation.service.repository.EvaluationCourseRepo;
import com.evaluation.service.repository.EvaluationQuestionRepo;
import com.evaluation.service.repository.StudentEvaluatorRepo;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {

    @Autowired
    EvaluationCourseRepo evaluationCourseRepo;

    @Autowired
    EvaluationQuestionRepo evaluationQuestionRepo;

    @Autowired
    StudentEvaluatorRepo studentEvaluatorRepo;

    @Autowired
    ChainBlockRepo chainBlockRepo;

    public List<EvaluationCourse> getListOfCourses(){
        return evaluationCourseRepo.findAll();
    }

    public List<EvaluationQuestion> getQuestions(){
        return evaluationQuestionRepo.findAll();
    }

    public Boolean postSubmission(Submission submission){
        Optional<EvaluationCourse> courseOptional = evaluationCourseRepo.findById(submission.getCourseId());
        if(!courseOptional.isPresent()){
            return false;
        }
        StudentEvaluator studentEvaluator = new StudentEvaluator();
        studentEvaluator.setName(submission.getName());
        studentEvaluator.setCourse(courseOptional.get());
        studentEvaluator.setStudentId(submission.getStudentId());
        studentEvaluator.setSubmitDate(LocalDateTime.now());
        studentEvaluator.setSolution(submission.getEvaluationResponse());
        if(storeHashedSubmission(submission)){
            studentEvaluatorRepo.save(studentEvaluator);
            return true;
        }
        return false;
    }

    public List<StudentEvaluator> getSubmissions(Long courseId){
        return studentEvaluatorRepo.findByCourse_Id(courseId);
    }

    public List<String> getChain(){
        return chainBlockRepo.getChain();
    }

    public String checkChainValidity(){
        int chainInvalidPos = validateChain();
        if(chainInvalidPos>0){
            return "Chain is invalid at position "+(chainInvalidPos+1);
        }
        return "The chain is valid";
    }

    private Integer validateChain(){
        List<ChainBlock> blockChain = chainBlockRepo.findAll();
        for(int i=1; i<blockChain.size();i++){
            ChainBlock currentBlock = blockChain.get(i);
            ChainBlock previousBlock = blockChain.get(i-1);
            Optional<StudentEvaluator> currentEvaluation = studentEvaluatorRepo.findByChainBlock_Id(currentBlock.getId());
            Optional<StudentEvaluator> previousEvaluation = studentEvaluatorRepo.findByChainBlock_Id(previousBlock.getId());
            if(!previousEvaluation.isPresent() || !currentEvaluation.isPresent())
                return i;
            Submission submission = new Submission(currentEvaluation.get(),currentBlock.getPreviousHash());
            if(!currentBlock.getCurrentHash().equals(hashSubmission(submission,currentBlock.getPreviousHash()))){
                return i;
            }
            Submission pSubmission = new Submission(currentEvaluation.get(),previousBlock.getCurrentHash());
            if(!currentBlock.getPreviousHash().equals(hashSubmission(pSubmission,previousBlock.getCurrentHash()))){
                return i;
            }
        }
        return 0;
    }

    private String hashSubmission(Submission submission, String previousHash){
        submission.setPreviousHash(previousHash);
        String stringSubmission = new Gson().toJson(submission);
        try {
            return HashingService.applyHash(stringSubmission,HashingService.SHA_256);
        }catch (Exception e){
            return "";
        }
    }
    private boolean storeHashedSubmission(Submission submission){
        Optional<ChainBlock> previous = chainBlockRepo.findFirstByOrderByIdDesc();
        String previousHash = previous.isPresent() ? previous.get().getCurrentHash() : null;
        String newHash = hashSubmission(submission,previousHash);
        if(!Strings.isNullOrEmpty(newHash)){
            chainBlockRepo.save(new ChainBlock(newHash, previousHash));
            return true;
        }
        return false;
    }
}
