package com.example.questifysharedapi.mapper;


import com.example.questifysharedapi.dto.AnswerDTO;
import com.example.questifysharedapi.dto.QuestionDTO;
import com.example.questifysharedapi.dto.request.QuestionRequest;
import com.example.questifysharedapi.model.Answer;
import com.example.questifysharedapi.model.Question;
import com.example.questifysharedapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MapperQuestion {

    @Mappings({
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "user.name", target = "nameUser"),
            @Mapping(source = "previousVersion.id", target = "previousId"),
            @Mapping(source = "createdAt", target = "createdAt", dateFormat = "dd/MM/yyyy")
    })
    QuestionDTO toQuestionDTO(Question question);

    @Mapping(source = "userId", target = "user")
    Question toQuestion(QuestionRequest questionRequest);

    AnswerDTO toAnswerDTO(Answer answer);
    Answer toAnswer(AnswerDTO answerDTO);


    List<QuestionDTO> toQuestionsDTO(List<Question> questions);
    List<Question> toQuestions(List<QuestionDTO> questionDTOS);

    List<AnswerDTO> toAnswersDTO(List<Answer> answers);
    List<Answer> toAnswers(List<AnswerDTO> answerDTOS);


    default User mapUser(Long id) {
        if (id == null) return null;
        User user = User.builder()
                .id(id)
                .build();
        return user;
    }

}
