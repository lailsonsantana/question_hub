package com.example.questifysharedapi.service;

import com.example.questifysharedapi.dto.CommentRecordDTO;
import com.example.questifysharedapi.exception.QuestionNotFound;
import com.example.questifysharedapi.exception.UserNotFound;
import com.example.questifysharedapi.model.Comment;
import com.example.questifysharedapi.model.Question;
import com.example.questifysharedapi.model.User;
import com.example.questifysharedapi.repository.CommentRepository;
import com.example.questifysharedapi.repository.QuestionRepository;
import com.example.questifysharedapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;


    @Transactional
    public Comment saveComment(CommentRecordDTO commentRecordDTO){
        Comment comment = new Comment();
        comment.setText(commentRecordDTO.text());

        Optional<Question> optionalQuestion = questionRepository.findById(commentRecordDTO.questionId());
        Optional<User> optionalUser = userRepository.findById(commentRecordDTO.userId());

        if(optionalUser.isPresent()){
            comment.setUser(optionalUser.get());
        }else{
            throw  new UserNotFound("User Not Found");
        }

        if(optionalQuestion.isPresent()){
            comment.setQuestion(optionalQuestion.get());
        }else{
            throw new QuestionNotFound("Question Not Found");
        }

        return  commentRepository.save(comment);
    }

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    @Transactional
    public List<CommentRecordDTO> getCommentsByQuestionId(Long questionId) {
        List<Comment> comments = new ArrayList<>();
        comments = commentRepository.findAllByQuestionId(questionId);
        return comments.stream()
                .map(comment -> new CommentRecordDTO(
                        comment.getId(),
                        comment.getText(),
                        comment.getUser().getId(),
                        comment.getQuestion().getId(),
                        comment.getUser().getName(),
                        formatDate(comment.getCreatedAt())
                ))
                .collect(Collectors.toList());
    }

    public String formatDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
        String formattedDate = date.format(formatter);

        return formattedDate;
    }
}
