package com.example.questifysharedapi.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record QuestionDTO(
        Long id,
        @NotBlank
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


