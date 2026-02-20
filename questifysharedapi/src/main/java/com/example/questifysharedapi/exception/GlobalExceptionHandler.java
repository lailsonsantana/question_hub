package com.example.questifysharedapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ProblemDetail> internalErrorHandler(RuntimeException exception){
        return getDetails(exception, HttpStatus.INTERNAL_SERVER_ERROR, "PROBLEMAS INTERNOS");
    }

    @ExceptionHandler(DuplicatedException.class)
    private ResponseEntity<ProblemDetail> duplicatedHandler(DuplicatedException exception){

        return getDetails(exception, HttpStatus.CONFLICT, "RECURSO DUPLICADO");
    }

    @ExceptionHandler(InvalidVersionException.class)
    private ResponseEntity<ProblemDetail> invalidVersionHandler(InvalidVersionException exception){
        return getDetails(exception, HttpStatus.BAD_REQUEST, "VERSÃO INVÁLIDA");
    }

    @ExceptionHandler(InappropriateContentException.class)
    public ResponseEntity<ProblemDetail> inappropriateContentHandler(InappropriateContentException exception) {
        return getDetails(exception, HttpStatus.BAD_REQUEST, "CONTEÚDO INAPROPRIADO");
    }

    @ExceptionHandler(UserNotFound.class)
    private ResponseEntity<ProblemDetail> userNotExistHandler(UserNotFound exception){
        return getDetails(exception, HttpStatus.NOT_FOUND, "USUÁRIO NÃO ENCONTRADO");
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    private ResponseEntity<ProblemDetail> questionNotFound(QuestionNotFoundException exception){
        return getDetails(exception, HttpStatus.UNAUTHORIZED, "QUESTÃO NÃO ENCONTRADA");
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    private ResponseEntity<ProblemDetail> incorrectPasswordHandler(IncorrectPasswordException exception){
        return getDetails(exception, HttpStatus.UNAUTHORIZED, "SENHA INCORRETA");
    }

    @ExceptionHandler(APIKeyException.class)
    private ResponseEntity<ProblemDetail> apiKeyHandler(APIKeyException exception){
        return getDetails(exception, HttpStatus.INTERNAL_SERVER_ERROR, "CONTEÚDO INAPROPRIADO");
    }

    private ResponseEntity<ProblemDetail> getDetails(Exception exception, HttpStatus httpStatus, String title){
        ProblemDetail problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(title);
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

}
