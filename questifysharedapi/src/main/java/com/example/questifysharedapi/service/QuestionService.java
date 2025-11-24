package com.example.questifysharedapi.service;

import com.example.questifysharedapi.dto.QuestionRecordDTO;
import com.example.questifysharedapi.exception.InappropriateContentException;
import com.example.questifysharedapi.exception.InvalidVersionException;
import com.example.questifysharedapi.exception.QuestionNotFound;
import com.example.questifysharedapi.mapper.MapperQuestion;
import com.example.questifysharedapi.model.Answer;
import com.example.questifysharedapi.model.Question;
import com.example.questifysharedapi.model.User;
import com.example.questifysharedapi.repository.AnswerRepository;
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
    public Question saveQuestion(QuestionRecordDTO questionRecordDTO){

        if(verifyStatement(questionRecordDTO.statement())){

            Question question = mapperQuestion.toQuestion(questionRecordDTO);
            if(questionRecordDTO.userId() != null){
                Optional<User> possibleUser = userRepository.findById(questionRecordDTO.userId());
                possibleUser.ifPresent(question::setUser);
            }
            question.setCountRating(0);
            question.setTotalRating(0d);
            question.getAnswers().forEach(answer -> answer.setQuestion(question));

            return questionRepository.save(question);
        }
        throw new InappropriateContentException("Esse conteúdo é irrelevante ou inapropriado.");
    }

    @Transactional
    public Question saveNewVersion(QuestionRecordDTO questionRecordDTO , Long id){

        Question previousQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFound("Question Not Found"));

        if (previousQuestion.getPreviousVersion() != null) {
            throw new InvalidVersionException("This question is a version of another question");
        }

        boolean isValid = verifyStatement(questionRecordDTO.statement());
        if (!isValid) {
            throw new InappropriateContentException("This content is Inappropriate.");
        }

        Question question = mapperQuestion.toQuestion(questionRecordDTO);

        if (questionRecordDTO.userId() != null) {
            userRepository.findById(questionRecordDTO.userId())
                    .ifPresent(question::setUser);
        }

        question.setCountRating(0);
        question.setTotalRating(0d);
        question.setPreviousVersion(previousQuestion);
        question.getAnswers().forEach(answer -> answer.setQuestion(question));

        return questionRepository.save(question);
    }

    public Boolean verifyStatement(String statement){

        String response = openAiService.getClassification(statement);

        log.info("Response of Model {}" , response);
        log.info("Response of equals {}" , !response.equals("INADEQUADO"));
        return !response.equals("INADEQUADO");
    }

    @Transactional
    public List<QuestionRecordDTO> getAllQuestions(){

        List<Question> questions = questionRepository.findAllByOrderByIdAsc();
        return mapperQuestion.toQuestionsDTO(questions);
    }

    @Transactional
    public List<QuestionRecordDTO> filterQuestions(List<String> disciplines) {
        List<Question> questions = new ArrayList<>();
        for(String discipline : disciplines){
            questions.addAll(questionRepository.findAllByDiscipline(discipline));
        }
        return mapperQuestion.toQuestionsDTO(questions);
    }

    @Transactional
    public List<QuestionRecordDTO> getAllByUser(Long userId){

        List<Question> questions = questionRepository.findAllByOrderByIdAsc();

        return mapperQuestion.toQuestionsDTO(
               questions.stream().filter(question -> question.getUser().getId()
                       .equals(userId)).toList()
        );
    }

    @Transactional 
    public QuestionRecordDTO getQuestionById(Long questionId){

        Optional<Question> existingQuestion = questionRepository.findById(questionId);
        if(existingQuestion.isPresent()){
            return mapperQuestion.toQuestionDTO(existingQuestion.get());
        }
        throw new QuestionNotFound("Question Not Found");
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
            throw new QuestionNotFound("Question Not Found");
        }


        int newCount = question.getCountRating() == null? 0 : question.getCountRating() + 1;
        double newTotalRating = question.getTotalRating() == null? 0 : question.getTotalRating() + newRating;

        question.setCountRating(newCount);
        question.setTotalRating(newTotalRating);

        questionRepository.save(question);

        return newTotalRating / newCount ;
    }

}
