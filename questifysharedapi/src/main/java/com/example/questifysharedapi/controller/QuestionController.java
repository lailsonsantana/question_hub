package com.example.questifysharedapi.controller;

import com.example.questifysharedapi.dto.QuestionDTO;
import com.example.questifysharedapi.model.Question;
import com.example.questifysharedapi.service.QuestionService;
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
    public ResponseEntity<?> saveQuestion(@RequestBody QuestionDTO questionDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.saveQuestion(questionDTO));
    }

    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions(){

        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<QuestionDTO>> filterQuestions(@RequestParam List<String> disciplines){
        
        List<QuestionDTO> qdto = questionService.filterQuestions(disciplines);
        return ResponseEntity.ok(qdto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByUserId(@PathVariable Long id){

        List<QuestionDTO> qdto = questionService.getAllByUser(id);
        return ResponseEntity.ok(qdto);
    }

    @GetMapping("questionId/{id}")
    public ResponseEntity<QuestionDTO> getQuestionsById(@PathVariable Long id){

        QuestionDTO qdto = questionService.getQuestionById(id);
        return ResponseEntity.ok(qdto);
    }

    @PostMapping("/new-version/{id}")
    public ResponseEntity<Question> saveNewVersion(@PathVariable Long id,@RequestBody QuestionDTO questionDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.saveNewVersion(questionDTO, id));
        
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
