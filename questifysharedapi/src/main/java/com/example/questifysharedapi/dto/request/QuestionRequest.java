package com.example.questifysharedapi.dto.request;

import com.example.questifysharedapi.dto.AnswerDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record QuestionRequest(
        @NotBlank
        String statement,
        String discipline,
        List<AnswerDTO> answers,
        Long userId,
        Long previousId,
        String justification,
        int countRating,
        Double totalRating) implements Serializable {
}
