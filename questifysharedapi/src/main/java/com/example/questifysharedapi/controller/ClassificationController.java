package com.example.questifysharedapi.controller;

import com.example.questifysharedapi.dto.ClassificationDTO;
import com.example.questifysharedapi.model.Classification;
import com.example.questifysharedapi.service.ClassificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/classifications")
@Slf4j // Annotation to log creation
@RequiredArgsConstructor
public class ClassificationController {

    private final ClassificationService classificationService;

    @PostMapping
    public ResponseEntity<Classification> saveClassification(@RequestBody ClassificationDTO classificationDTO){
        log.info("CLASSIFICATION  {}", classificationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(classificationService.saveClassification(classificationDTO));

    }

    @GetMapping("{questionId}/{userId}")
    public ResponseEntity<Double> getClassificationByUserAndQuestion(@PathVariable Long questionId ,
                                                                                      @PathVariable Long userId){
        log.info("ESSE MÉTODO FOI CHAMADO");
        return ResponseEntity.ok(classificationService.getClassificationByUserAndQuestion(questionId,userId));
    }

}
