package com.example.questifysharedapi.model;


import com.example.questifysharedapi.dto.UserDTO;

public final class UserTestBuilder {

    public static User createUser(){
        return User.builder()
                .id(1L)
                .name("Mary Kate")
                .email("marykate@gmail.com")
                .password("mary123")
                .build();
    }

    public static UserDTO createUserDTO(){
        return UserDTO.builder()
                .id(1L)
                .name("Mary Kate")
                .email("marykate@gmail.com")
                .password("mary123")
                .build();
    }
}
