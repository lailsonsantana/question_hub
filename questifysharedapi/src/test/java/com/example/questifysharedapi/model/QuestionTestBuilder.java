package com.example.questifysharedapi.model;

import com.example.questifysharedapi.dto.AnswerDTO;
import com.example.questifysharedapi.dto.QuestionDTO;
import com.example.questifysharedapi.dto.request.QuestionRequest;
import com.example.questifysharedapi.dto.response.QuestionResponse;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionTestBuilder {

    public static Question createQuestion(){
        return Question.builder()
                .id(1L)
                .statement("How many countries are there in the Europe ?")
                .previousVersion(createPreviousQuestion())
                .answers(createAnswers())
                .discipline("Math")
                .build();
    }

    public static Question createPreviousQuestion(){
        return Question.builder()
                .id(2L)
                .statement("How many countries are there in the world ?")
                .discipline("Math")
                .build();
    }

    public static QuestionRequest createQuestionRequest(){
        return QuestionRequest.builder()
                .statement("How many countries are there in the Europe ?")
                .discipline("Math")
                .answers(createAnswersDTO())
                .build();
    }

    public static QuestionResponse createQuestionResponse(){
        return QuestionResponse.builder()
                .id(1L)
                .statement("How many countries are there in the Europe ?")
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

    public static List<Answer> createAnswers(){
        Question question = new Question();
        return new ArrayList<>(Arrays.asList(
                new Answer(1L,"Test1" , false, question),
                new Answer(2L,"Test2" , false, question),
                new Answer(3L,"Test3" , false, question),
                new Answer(4L,"Test4" , false, question),
                new Answer(5L,"Test5" ,  true, question)
        ));
    }
}
