package com.example.questifysharedapi.dto;


public record UserDTO(Long id,
                      String name,
                      String email,
                      String password,
                      String role ,
                      Long questionId) {
}
