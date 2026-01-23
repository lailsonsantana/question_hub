package com.example.questifysharedapi.model;

import com.example.questifysharedapi.dto.AnswerDTO;
import com.example.questifysharedapi.dto.QuestionDTO;
import com.example.questifysharedapi.dto.request.QuestionRequest;
import com.example.questifysharedapi.dto.response.QuestionResponse;
import org.mockito.stubbing.Answer6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionTestBuilder {

    public static Question createQuestion(){
        return Question.builder()
                .id(1L)
                .statement("How many countries are there in the world ?")
                .discipline("Math")
                .build();
    }

    public static QuestionRequest createQuestionRequest(){
        return QuestionRequest.builder()
                .statement("How many countries are there in the world ?")
                .discipline("Math")
                .answers(createAnswersDTO())
                .build();
    }

    public static QuestionResponse createQuestionResponse(){
        return QuestionResponse.builder()
                .id(1L)
                .statement("How many countries are there in the world ?")
                .discipline("Math")
                .answers(createAnswersDTO())
                .build();
    }

    public static List<AnswerDTO> createAnswersDTO(){

        return new ArrayList<>(Arrays.asList(
                new AnswerDTO("Test1" , false),
                new AnswerDTO("Test2" , false),
                new AnswerDTO("Test3" , false),
                new AnswerDTO("Test4" , false),
                new AnswerDTO("Test5" , true)
        ));
    }
}
