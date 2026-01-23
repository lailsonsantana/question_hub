package com.example.questifysharedapi.dto.response;

import com.example.questifysharedapi.dto.AnswerDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record QuestionResponse(
        Long id,
        @NotBlank
        String statement,
        String discipline,
        List<AnswerDTO> answers,
        String nameUser,
        Long previousId,
        String justification,
        String createdAt,
        int countRating,
        Double totalRating) implements Serializable {
}
