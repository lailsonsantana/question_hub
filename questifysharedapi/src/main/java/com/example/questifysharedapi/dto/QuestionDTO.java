package com.example.questifysharedapi.dto;


import java.io.Serializable;
import java.util.List;

public record QuestionDTO(Long id,
                          String statement,
                          String discipline,
                          List<AnswerDTO> answers,
                          Long userId,
                          String nameUser,
                          Long previousId,
                          String justification,
                          String createdAt,
                          int countRating,
                          Double totalRating) implements Serializable {
}


