package com.example.questifysharedapi.model;

import com.example.questifysharedapi.dto.QuestionDTO;

public class QuestionTestBuilder {

    public static Question createQuestion(){
        return Question.builder()
                .id(1L)
                .statement("How many countries are there in the world ?")
                .discipline("Math")
                .build();
    }

    public static QuestionDTO createQuestionDTO(){
        return QuestionDTO.builder()
                .id(1L)
                .statement("How many countries are there in the world ?")
                .discipline("Math")
                .build();
    }
}
