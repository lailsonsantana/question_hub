package com.example.questifysharedapi.controller;

import com.example.questifysharedapi.dto.ContextRecordDTO;
import com.example.questifysharedapi.dto.CredentialsDTO;
import com.example.questifysharedapi.dto.UserRecordDTO;
import com.example.questifysharedapi.model.Context;
import com.example.questifysharedapi.model.User;
import com.example.questifysharedapi.service.ContextService;
import com.example.questifysharedapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j // Annotation to log creation
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final ContextService contextService;
    

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody UserRecordDTO userRecordDTO){
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userRecordDTO));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody CredentialsDTO credentials){
        var token = userService.authenticate(credentials.getEmail(), credentials.getPassword());

        if(token == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(token);
    }

    @PostMapping("/setContext")
    public ResponseEntity<Context> setUserContext(@RequestBody String newContext) {
        ContextRecordDTO contextRecordDTO = new ContextRecordDTO(newContext);
        return ResponseEntity.status(HttpStatus.CREATED).body(contextService.saveContext(contextRecordDTO));
    }
}
