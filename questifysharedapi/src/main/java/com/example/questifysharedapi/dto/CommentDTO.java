package com.example.questifysharedapi.dto;


public record CommentDTO(Long id,
                         String text,
                         Long userId,
                         Long questionId,
                         String nameUser,
                         String createdAt) {
}
