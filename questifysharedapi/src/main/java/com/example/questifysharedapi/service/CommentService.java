package com.example.questifysharedapi.service;

import com.example.questifysharedapi.dto.CommentDTO;
import com.example.questifysharedapi.exception.QuestionNotFoundException;
import com.example.questifysharedapi.exception.UserNotFound;
import com.example.questifysharedapi.mapper.MapperComment;
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

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final MapperComment mapperComment;

    @Transactional
    public Comment saveComment(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setText(commentDTO.text());

        Optional<Question> optionalQuestion = questionRepository.findById(commentDTO.questionId());
        Optional<User> optionalUser = userRepository.findById(commentDTO.userId());

        if(optionalUser.isPresent()){
            comment.setUser(optionalUser.get());
        }else{
            throw  new UserNotFound("User Not Found");
        }

        if(optionalQuestion.isPresent()){
            comment.setQuestion(optionalQuestion.get());
        }else{
            throw new QuestionNotFoundException("Question Not Found");
        }

        return  commentRepository.save(comment);
    }

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    @Transactional
    public List<CommentDTO> getCommentsByQuestionId(Long questionId) {

        return mapperComment.toCommentsDTO(commentRepository.findAllByQuestionId(questionId));

    }

}
