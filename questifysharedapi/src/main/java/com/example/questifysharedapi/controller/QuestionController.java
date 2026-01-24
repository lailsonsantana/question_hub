package com.example.questifysharedapi.controller;

import com.example.questifysharedapi.dto.QuestionDTO;
import com.example.questifysharedapi.dto.request.QuestionRequest;
import com.example.questifysharedapi.dto.response.QuestionResponse;
import com.example.questifysharedapi.model.Question;
import com.example.questifysharedapi.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/questions")
@Slf4j // Annotation to log creation
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<?> saveQuestion(@RequestBody @Valid QuestionRequest questionRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.saveQuestion(questionRequest));
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAllQuestions(){

        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<QuestionResponse>> filterQuestions(@RequestParam List<String> disciplines){

        return ResponseEntity.ok(questionService.filterQuestions(disciplines));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<QuestionResponse>> getQuestionsByUserId(@PathVariable Long id){

        return ResponseEntity.ok(questionService.getAllByUser(id));
    }

    @GetMapping("questionId/{id}")
    public ResponseEntity<QuestionResponse> getQuestionsById(@PathVariable Long id){

        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    @PostMapping("/new-version/{id}")
    public ResponseEntity<Question> saveNewVersion(@PathVariable Long id,@RequestBody QuestionRequest questionRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.saveNewVersion(questionRequest, id));
        
    }

    @PatchMapping("/update-rating/{newRating}/{questionId}")
    public ResponseEntity<Double> updateRating(@PathVariable Double newRating, @PathVariable Long questionId){

        return ResponseEntity.status(HttpStatus.OK).body(questionService.updateRating(newRating, questionId));
    }

    @GetMapping("rating/{questionId}")
    public ResponseEntity<Double> getRating(@PathVariable Long questionId) {
        return ResponseEntity.status(HttpStatus.OK).body(questionService.getRating(questionId));
    }
    
}
