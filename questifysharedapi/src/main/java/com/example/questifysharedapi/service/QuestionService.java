package com.example.questifysharedapi.service;

import com.example.questifysharedapi.dto.QuestionDTO;
import com.example.questifysharedapi.dto.request.QuestionRequest;
import com.example.questifysharedapi.exception.InappropriateContentException;
import com.example.questifysharedapi.exception.InvalidVersionException;
import com.example.questifysharedapi.exception.QuestionNotFoundException;
import com.example.questifysharedapi.mapper.MapperQuestion;
import com.example.questifysharedapi.model.Question;
import com.example.questifysharedapi.model.User;
import com.example.questifysharedapi.repository.QuestionRepository;
import com.example.questifysharedapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final OpenAiService openAiService;
    private final MapperQuestion mapperQuestion;

    @Transactional
    public Question saveQuestion(QuestionRequest questionRequest){


        if(openAiService.getClassification((questionRequest.statement()))){

            Question question = mapperQuestion.toQuestion(questionRequest);
            if(questionRequest.userId() != null){
                Optional<User> possibleUser = userRepository.findById(questionRequest.userId());
                possibleUser.ifPresent(question::setUser);
            }
            question.getAnswers().forEach(answer -> answer.setQuestion(question));

            return questionRepository.save(question);
        }

        throw new InappropriateContentException("Esse conteúdo é irrelevante ou inapropriado.");
    }

    @Transactional
    public Question saveNewVersion(QuestionRequest questionRequest, Long id){

        Question previousQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question Not Found"));

        if (previousQuestion.getPreviousVersion() != null) {
            throw new InvalidVersionException("This question is a version of another question");
        }

        boolean isValid = verifyStatement(questionRequest.statement());
        if (!isValid) {
            throw new InappropriateContentException("This content is Inappropriate.");
        }

        Question question = mapperQuestion.toQuestion(questionRequest);

        if (questionRequest.userId() != null) {
            userRepository.findById(questionRequest.userId()).ifPresent(question::setUser);
        }

        question.setPreviousVersion(previousQuestion);
        question.getAnswers().forEach(answer -> answer.setQuestion(question));

        return questionRepository.save(question);
    }

    public Boolean verifyStatement(String statement){

        //String response = openAiService.getClassification(statement);

        // Modificando aqui por que a assinatura do gpt expirou
        return false;
    }

    @Transactional
    public List<QuestionDTO> getAllQuestions(){
        //Page<Question> pageQuestion = questionRepository.findAll(PageRequest.of(0, 10));
        List<Question> questions = questionRepository.findAll();
        return mapperQuestion.toQuestionsDTO(questions);
    }

    @Transactional
    public List<QuestionDTO> filterQuestions(List<String> disciplines) {
        List<Question> questions = new ArrayList<>();
        for(String discipline : disciplines){
            questions.addAll(questionRepository.findAllByDiscipline(discipline));
        }
        return mapperQuestion.toQuestionsDTO(questions);
    }

    @Transactional
    public List<QuestionDTO> getAllByUser(Long userId){

        List<Question> questions = questionRepository.findAllByOrderByIdAsc();

        return mapperQuestion.toQuestionsDTO(
               questions.stream().filter(question -> question.getUser().getId()
                       .equals(userId)).toList()
        );
    }

    @Transactional 
    public QuestionDTO getQuestionById(Long questionId){

        Optional<Question> existingQuestion = questionRepository.findById(questionId);

        if(existingQuestion.isPresent()){
            return mapperQuestion.toQuestionDTO(existingQuestion.get());
        }
        throw new QuestionNotFoundException("Question Not Found");
    }

    @Transactional
    public Double getRating(Long questionId){
        Question question = new Question();
        Optional<Question> existingQuestion = questionRepository.findById(questionId);

        if(existingQuestion.isPresent()){
            question = existingQuestion.get();
            return question.getTotalRating() / question.getCountRating();
        }
        
        return 0.0;  
    }

    @Transactional
    public Double updateRating(Double newRating, Long questionId){

        Optional<Question> existingQuestion = questionRepository.findById(questionId);
        Question question = new Question();
        if(existingQuestion.isPresent()){
            question = existingQuestion.get();
        }
        else{
            throw new QuestionNotFoundException("Question Not Found");
        }


        int newCount = question.getCountRating() == null? 0 : question.getCountRating() + 1;
        double newTotalRating = question.getTotalRating() == null? 0 : question.getTotalRating() + newRating;

        question.setCountRating(newCount);
        question.setTotalRating(newTotalRating);

        questionRepository.save(question);

        return newTotalRating / newCount ;
    }

}
