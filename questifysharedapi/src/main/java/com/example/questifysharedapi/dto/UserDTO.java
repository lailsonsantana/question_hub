package com.example.questifysharedapi.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        Long id,
        @NotBlank(message = "Nome é obrigatório")
        String name,
        @Email(message = "Informe um email válido")
        String email,
        String password,
        String role ,
        Long questionId) {
}
