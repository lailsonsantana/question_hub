package com.example.questifysharedapi.factory;

import com.example.questifysharedapi.dto.AnswerDTO;
import com.example.questifysharedapi.dto.QuestionDTO;
import com.example.questifysharedapi.model.Answer;
import com.example.questifysharedapi.model.Question;
import com.example.questifysharedapi.model.User;
import com.example.questifysharedapi.model.UserRole;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public abstract class QuestionFactory {


    public static User createValidUser() {
        return new User(3L, "Margaret" , "lailsonbit@gmail.com", "lailson123abc", LocalDateTime.now(),
                UserRole.STUDENT, null , null , null);
    }
    public static Question createValidQuestion() {
        return new Question(3L , "Teste" , "Math" , createValidAnswers() , null , null ,
                createValidUser() , createValidQuestionWithoutPrevious() , null , "Não Tem" , null ,  3 , 4.6);
    }

    public static Question createValidQuestionWithoutPrevious() {
        return new Question(4L , "Teste" , "Math" , createValidAnswers() , null , null ,
                createValidUser() , null , null , "Não Tem" , null ,  3 , 4.6);
    }

    public static QuestionDTO createValidQuestionDTO(){
        return new QuestionDTO(3L, "Teste", "Math", createValidAnswersDTO(), 3L, "Margaret",
            4L, "Não Tem", null, 3, 4.6);
    }

    public static QuestionDTO createValidQuestionDTOWithoutPrevious(){
        return new QuestionDTO(4L, "Teste", "Math", null, 3L, "Margaret",
                null, "Não Tem", null, 3, 4.6);
    }

    public static List<Answer> createValidAnswers(){
        Answer answer1 = new Answer(1L, "Teste1", false, null);
        Answer answer2 = new Answer(2L, "Teste2", false, null);
        Answer answer3 = new Answer(3L,"Teste3", false, null);
        Answer answer4 = new Answer(4L,"Teste4", true, null);
        Answer answer5 = new Answer(5L,"Teste5", false, null);
        List<Answer> answers = new ArrayList<>();
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        answers.add(answer4);
        answers.add(answer5);

        return answers;
    }

    public static List<AnswerDTO> createValidAnswersDTO(){
        AnswerDTO answerDTO1 = new AnswerDTO("Teste1", false);
        AnswerDTO answerDTO2 = new AnswerDTO("Teste2", false);
        AnswerDTO answerDTO3 = new AnswerDTO("Teste3", false);
        AnswerDTO answerDTO4 = new AnswerDTO("Teste4", true);
        AnswerDTO answerDTO5 = new AnswerDTO("Teste5", false);
        List<AnswerDTO> answerDTOS = new ArrayList<>();
        answerDTOS.add(answerDTO1);
        answerDTOS.add(answerDTO2);
        answerDTOS.add(answerDTO3);
        answerDTOS.add(answerDTO4);
        answerDTOS.add(answerDTO5);

        return answerDTOS;
    }
}
