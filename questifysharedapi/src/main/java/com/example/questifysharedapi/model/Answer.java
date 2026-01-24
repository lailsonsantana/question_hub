package com.example.questifysharedapi.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_ANSWER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String text;

    private Boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id",nullable = false) // foreigner key
    @JsonIgnore
    private Question question;

}
