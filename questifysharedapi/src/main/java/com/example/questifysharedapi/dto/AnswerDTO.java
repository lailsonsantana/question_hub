package com.example.questifysharedapi.dto;

import java.io.Serializable;

public record AnswerDTO(String text , Boolean isCorrect)
implements Serializable {
}
