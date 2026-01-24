package com.example.questifysharedapi.service;

import com.example.questifysharedapi.dto.UserDTO;
import com.example.questifysharedapi.exception.DuplicatedException;
import com.example.questifysharedapi.mapper.MapperUser;
import com.example.questifysharedapi.mapper.MapperUserImpl;
import com.example.questifysharedapi.model.User;
import com.example.questifysharedapi.model.UserTestBuilder;
import com.example.questifysharedapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Spy
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Spy
    private MapperUser mapperUser = new MapperUserImpl();

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> passwordCaptor;

    private User user;

    private UserDTO userDTO;

    @BeforeEach
    void setUp(){
        this.user = UserTestBuilder.createUser();
        this.userDTO = UserTestBuilder.createUserDTO();
    }

    @Nested
    class saveUser{
        @Test
        void saveUserWithSuccess() {
            /* ARRANGE
            For default the Spring return the command below like null
            Mockito.when(userRepository.findByEmail("marykate@gmail.com")).thenReturn(null);

            How I'm using spy annotation then I don't need mocking this class
            However if someday I will have problem with mapper class this test will go to fail
            Mockito.when(mapperUser.toUser(userDTO)).thenReturn(user);
            Mockito.when(mapperUser.toUserDTO(user)).thenReturn(userDTO); */

            Mockito.when(passwordEncoder.encode("mary123")).thenReturn("mary123");
            Mockito.doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            // ACT
            var output = userService.saveUser(userDTO);
            System.out.println(" OUTPUT " +  output);

            var userCaptured = userArgumentCaptor.getValue();

            // ASSERT
            assertEquals(output.name() , userCaptured.getName());
            assertEquals(output.email() , userCaptured.getEmail());
            assertEquals(output.password() , userCaptured.getPassword());
        }


        @Test
        void shouldReturnUserAlreadyExists(){

            Mockito.when(userRepository.findByEmail("marykate@gmail.com")).thenReturn(user);
            assertThrows(DuplicatedException.class, () -> userService.saveUser(userDTO));
        }
    }



    @Test
    void getAllUsers() {
    }
}