package com.example.questifysharedapi.factory;

import com.example.questifysharedapi.dto.AnswerRecordDTO;
import com.example.questifysharedapi.dto.QuestionRecordDTO;
import com.example.questifysharedapi.mapper.MapperQuestion;
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

    public static QuestionRecordDTO createValidQuestionDTO(){
        return new QuestionRecordDTO(3L, "Teste", "Math", createValidAnswersDTO(), 3L, "Margaret",
            4L, "Não Tem", null, 3, 4.6);
    }

    public static QuestionRecordDTO createValidQuestionDTOWithoutPrevious(){
        return new QuestionRecordDTO(4L, "Teste", "Math", null, 3L, "Margaret",
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

    public static List<AnswerRecordDTO> createValidAnswersDTO(){
        AnswerRecordDTO answerRecordDTO1 = new AnswerRecordDTO("Teste1", false);
        AnswerRecordDTO answerRecordDTO2 = new AnswerRecordDTO("Teste2", false);
        AnswerRecordDTO answerRecordDTO3 = new AnswerRecordDTO("Teste3", false);
        AnswerRecordDTO answerRecordDTO4 = new AnswerRecordDTO("Teste4", true);
        AnswerRecordDTO answerRecordDTO5 = new AnswerRecordDTO("Teste5", false);
        List<AnswerRecordDTO> answerRecordDTOS = new ArrayList<>();
        answerRecordDTOS.add(answerRecordDTO1);
        answerRecordDTOS.add(answerRecordDTO2);
        answerRecordDTOS.add(answerRecordDTO3);
        answerRecordDTOS.add(answerRecordDTO4);
        answerRecordDTOS.add(answerRecordDTO5);

        return answerRecordDTOS;
    }
}
