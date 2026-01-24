package com.example.questifysharedapi.service;

import com.example.questifysharedapi.dto.request.QuestionRequest;
import com.example.questifysharedapi.dto.response.QuestionResponse;
import com.example.questifysharedapi.exception.InappropriateContentException;
import com.example.questifysharedapi.exception.InvalidVersionException;
import com.example.questifysharedapi.exception.QuestionNotFoundException;
import com.example.questifysharedapi.mapper.MapperQuestion;
import com.example.questifysharedapi.mapper.MapperQuestionImpl;
import com.example.questifysharedapi.model.Question;
import com.example.questifysharedapi.model.QuestionTestBuilder;
import com.example.questifysharedapi.model.User;
import com.example.questifysharedapi.model.UserTestBuilder;
import com.example.questifysharedapi.repository.QuestionRepository;
import com.example.questifysharedapi.repository.UserRepository;
import lombok.Data;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Data
@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @InjectMocks
    @Spy
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private UserRepository userRepository;

    @Spy
    private MapperQuestion mapperQuestion = new MapperQuestionImpl();

    @Mock
    private OpenAiService openAiService;

    @Captor
    private ArgumentCaptor<Question> questionArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> questionIdArgumentCaptor;

    private User user;
    private Question question;
    private Question previousQuestion;
    private QuestionResponse questionResponse;
    private QuestionRequest questionRequest;

    @BeforeEach
    void setUp(){

        user = UserTestBuilder.createUser();
        question = QuestionTestBuilder.createQuestion();
        previousQuestion = QuestionTestBuilder.createPreviousQuestion();
        questionRequest = QuestionTestBuilder.createQuestionRequest();
        questionResponse = QuestionTestBuilder.createQuestionResponse();
    }

    @Nested
    class saveQuestion{

        @Test
        @DisplayName("This method should save a question with successful")
        void shouldSaveAQuestionWithSuccess() {

            // ARRANGE
            Mockito.when(openAiService.getClassification(questionRequest.statement())).thenReturn(true);
            Mockito.when(questionRepository.save(questionArgumentCaptor.capture())).thenReturn(question);

            // ACT
            var output = questionService.saveQuestion(questionRequest);
            var questionCaptured = questionArgumentCaptor.getValue();

            // ASSERT
            assertEquals(output.discipline(), questionCaptured.getDiscipline());
            assertEquals(output.statement(), questionCaptured.getStatement());
            //assertIterableEquals(output.answers(), questionCaptured.getAnswers());

        }

        @Test
        @DisplayName("This method should return an InappropriateContentException when trying to save")
        void shouldReturnAnExceptionWhenTryToSave(){
            // ARRANGE
            Mockito.when(openAiService.getClassification(questionRequest.statement())).thenReturn(false);
            // ACT
            Executable action = () -> questionService.saveQuestion(questionRequest);

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
            Mockito.when(questionRepository.findById(3L)).thenReturn(Optional.empty());

            // ACT
            Executable action = () -> questionService.saveNewVersion(questionRequest, 3L);

            //ASSERT
            assertThrows(QuestionNotFoundException.class, action);
        }

        @Test
        @DisplayName("")
        @Disabled("Need to be fixed")
        void shouldReturnInvalidVersionException(){
            // ARRANGE
            Mockito.when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

            // ACT
            Executable action = () -> questionService.saveNewVersion(questionRequest, 1L);

            //ASSERT
            assertThrows(InvalidVersionException.class, action);
        }

        @Test
        @DisplayName("")
        void shouldReturnInappropriateContentException(){
            // ARRANGE
            Mockito.when(questionRepository.findById(2L)).thenReturn(Optional.of(previousQuestion));
            Mockito.when(openAiService.getClassification(questionRequest.statement())).thenReturn(false);

            // ACT
            Executable action = () -> questionService.saveNewVersion(questionRequest, 2L);

            //ASSERT
            assertThrows(InappropriateContentException.class, action);
        }

        @Test
        @DisplayName("")
        void shouldSaveANewVersionOfAQuestionWithSuccess(){
            // ARRANGE
            Mockito.when(questionRepository.findById(2L)).thenReturn(Optional.of(previousQuestion));
            Mockito.when(openAiService.getClassification(questionRequest.statement())).thenReturn(true);
            Mockito.when(questionRepository.save(questionArgumentCaptor.capture())).thenReturn(question);

            // ACT
            var output = questionService.saveNewVersion(questionRequest, 2L);
            var questionCaptured = questionArgumentCaptor.getValue();

            // ASSERT
            assertEquals(output.getStatement(), questionCaptured.getStatement());
            // assertIterableEquals(output.getAnswers(), questionCaptured.getAnswers());
            //assertEquals(output.getUser(), questionCaptured.getUser());
        }

    }

    @Nested
    class getAllQuestions{

        @Test
        void shouldReturnAllQuestionsWithSuccess(){

            // ARRANGE
            Mockito.when(questionRepository.findAllByOrderByIdAsc()).thenReturn(List.of(question));
            Mockito.when(mapperQuestion.toQuestionsResponse(List.of(question))).thenReturn(List.of(questionResponse));

            // ACT
            var output = questionService.getAllQuestions();

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
            when(questionRepository.findById(questionIdArgumentCaptor.capture())).thenReturn(Optional.of(question));
            when(mapperQuestion.toQuestionResponse(question)).thenReturn(questionResponse);

            // ACT
            var output = questionService.getQuestionById(1L);
            var idCaptured = questionIdArgumentCaptor.getValue();

            // ASSERT
            assertEquals(output.id() , idCaptured);
            assertEquals(output.statement() , questionResponse.statement());
        }

        @Test
        void shouldReturnQuestionNotFound(){

            // ARRANGE
            when(questionRepository.findById(questionIdArgumentCaptor.capture())).thenReturn(Optional.empty());

            // ACT
            Executable action = () -> questionService.getQuestionById(3L);

            //ASSERT
            assertThrows(QuestionNotFoundException.class, action);
        }
    }
}