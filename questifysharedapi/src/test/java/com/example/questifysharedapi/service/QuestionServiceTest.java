package com.example.questifysharedapi.service;

import com.example.questifysharedapi.exception.InappropriateContentException;
import com.example.questifysharedapi.exception.InvalidVersionException;
import com.example.questifysharedapi.exception.QuestionNotFound;
import com.example.questifysharedapi.factory.QuestionFactory;
import com.example.questifysharedapi.mapper.MapperQuestion;
import com.example.questifysharedapi.model.Question;
import com.example.questifysharedapi.repository.QuestionRepository;
import com.example.questifysharedapi.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MapperQuestion mapperQuestion;

    @Mock
    private OpenAiService openAiService;

    @Captor
    private ArgumentCaptor<Question> questionArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> questionIdArgumentCaptor;


    @Nested
    class saveQuestion{

        @Test
        @DisplayName("This method should save a question with successful")
        void shouldSaveAQuestionWithSuccess() {

            // ARRANGE
            var user = QuestionFactory.createValidUser();
            var question = QuestionFactory.createValidQuestion();
            var questionRecordDTO = QuestionFactory.createValidQuestionDTO();


            Mockito.when(userRepository.findById(questionRecordDTO.userId())).thenReturn(Optional.of(user));
            Mockito.when(openAiService.getClassification(questionRecordDTO.statement())).thenReturn("ADEQUADO");
            Mockito.when(mapperQuestion.toQuestion(questionRecordDTO)).thenReturn(question);
            Mockito.when(questionRepository.save(questionArgumentCaptor.capture())).thenReturn(question);

            // ACT
            var output = questionService.saveQuestion(questionRecordDTO);
            var questionCaptured = questionArgumentCaptor.getValue();

            // ASSERT
            assertEquals(output.getId(), questionCaptured.getId());
            assertEquals(output.getStatement(), questionCaptured.getStatement());
            assertIterableEquals(output.getAnswers(), questionCaptured.getAnswers());

        }

        @Test
        @DisplayName("This method should return an InappropriateContentException when trying to save")
        void shouldReturnAnExceptionWhenTryToSave(){

            // ARRANGE
            QuestionFactory.createValidQuestionDTO();
            var questionRecordDTO = QuestionFactory.createValidQuestionDTO();

            Mockito.when(openAiService.getClassification(questionRecordDTO.statement())).thenReturn("INADEQUADO");

            // ACT
            Executable action = () -> questionService.saveQuestion(questionRecordDTO);

            // ASSERT
            assertThrows(InappropriateContentException.class, action);

        }
    }

    @Nested
    class saveNewVersion{

        @Test
        @DisplayName("")
        void shouldReturnQuestionNotFoundException(){
            // ARRANGE
            var questionRecordDTO = QuestionFactory.createValidQuestionDTO();


            Mockito.when(questionRepository.findById(3L)).thenReturn(Optional.empty());

            // ACT
            Executable action = () -> questionService.saveNewVersion(questionRecordDTO, 3L);

            //ASSERT
            assertThrows(QuestionNotFound.class, action);
        }

        @Test
        @DisplayName("")
        void shouldReturnInvalidVersionException(){
            // ARRANGE
            var questionRecordDTO = QuestionFactory.createValidQuestionDTO();
            var question = QuestionFactory.createValidQuestion();


            Mockito.when(questionRepository.findById(3L)).thenReturn(Optional.of(question));

            // ACT
            Executable action = () -> questionService.saveNewVersion(questionRecordDTO, 3L);

            //ASSERT
            assertThrows(InvalidVersionException.class, action);
        }

        @Test
        @DisplayName("")
        void shouldReturnInappropriateContentException(){
            // ARRANGE
            var questionRecordDTO = QuestionFactory.createValidQuestionDTO();
            var question = QuestionFactory.createValidQuestionWithoutPrevious();


            Mockito.when(questionRepository.findById(4L)).thenReturn(Optional.of(question));
            Mockito.when(openAiService.getClassification(questionRecordDTO.statement())).thenReturn("INADEQUADO");

            // ACT
            Executable action = () -> questionService.saveNewVersion(questionRecordDTO, 4L);

            //ASSERT
            assertThrows(InappropriateContentException.class, action);
        }

        @Test
        @DisplayName("")
        void shouldSaveANewVersionOfAQuestionWithSuccess(){
            // ARRANGE

            // Version
            var user = QuestionFactory.createValidUser();
            var question = QuestionFactory.createValidQuestion();
            var questionRecordDTO = QuestionFactory.createValidQuestionDTO();
            // Original version
            var previousQuestion = QuestionFactory.createValidQuestionWithoutPrevious();
            var previousQuestionRecordDTO = QuestionFactory.createValidQuestionDTOWithoutPrevious();

            Mockito.when(questionRepository.findById(previousQuestionRecordDTO.id())).thenReturn(Optional.of(previousQuestion));
            Mockito.when(openAiService.getClassification(questionRecordDTO.statement())).thenReturn("ADEQUADO");
            Mockito.when(userRepository.findById(questionRecordDTO.userId())).thenReturn(Optional.of(user));
            Mockito.when(mapperQuestion.toQuestion(questionRecordDTO)).thenReturn(question);
            Mockito.when(questionRepository.save(questionArgumentCaptor.capture())).thenReturn(question);


            // ACT
            var output = questionService.saveNewVersion(questionRecordDTO, 4L);
            var questionCaptured = questionArgumentCaptor.getValue();

            // ASSERT
            assertEquals(output.getId(), questionCaptured.getId());
            assertEquals(output.getStatement(), questionCaptured.getStatement());
            assertIterableEquals(output.getAnswers(), questionCaptured.getAnswers());
            assertEquals(output.getUser(), questionCaptured.getUser());
        }

    }

    @Nested
    class getAllQuestions{

        @Test
        void shouldReturnAllQuestionsWithSuccess(){

            // ARRANGE
            var question = QuestionFactory.createValidQuestion();
            var questionRecordDTO = QuestionFactory.createValidQuestionDTO();


            Mockito.when(questionRepository.findAllByOrderByIdAsc()).thenReturn(List.of(question));
            Mockito.when(mapperQuestion.toQuestionsDTO(List.of(question))).thenReturn(List.of(questionRecordDTO));

            // ACT
            var output = questionService.getAllQuestions();
            System.out.println("Output =" + output);
            // ASSERT
            assertNotNull(output);
            assertEquals(1 , output.size());

        }
    }

    @Nested
    class getQuestionById{

        @Test
        void shouldReturnAQuestionWithSuccess(){
            // ARRANGE
            var question = QuestionFactory.createValidQuestion();
            var questionRecordDTO = QuestionFactory.createValidQuestionDTO();

            when(questionRepository.findById(questionIdArgumentCaptor.capture())).thenReturn(Optional.of(question));
            when(mapperQuestion.toQuestionDTO(question)).thenReturn(questionRecordDTO);

            // ACT
            var output = questionService.getQuestionById(3L);
            var idCaptured = questionIdArgumentCaptor.getValue();

            // ASSERT
            assertEquals(output.id() , idCaptured);
            assertEquals(output.statement() , questionRecordDTO.statement());
        }

        @Test
        void shouldReturnQuestionNotFound(){

            // ARRANGE
            when(questionRepository.findById(questionIdArgumentCaptor.capture())).thenReturn(Optional.empty());

            // ACT
            Executable action = () -> questionService.getQuestionById(3L);

            //ASSERT
            assertThrows(QuestionNotFound.class, action);
        }
    }


}