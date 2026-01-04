package com.example.questifysharedapi.service;

import com.example.questifysharedapi.dto.UserDTO;
import com.example.questifysharedapi.exception.DuplicatedException;
import com.example.questifysharedapi.model.User;
import com.example.questifysharedapi.model.UserRole;
import com.example.questifysharedapi.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> passwordCaptor;

    @Nested
    class saveUser{
        @Test
        void saveUserWithSuccess() {
            // ARRANGE

            var userRecordDTO = new UserDTO(2L , "Lailson" , "lailsonbit@gmail.com",
                   "lailson123" , "STUDENT" , 1L);

            var user = new User(2L, "Lailson" , "lailsonbit@gmail.com", "lailson123abc", LocalDateTime.now(),
                    UserRole.STUDENT, null , null , null);

            Mockito.doReturn("lailson123abc").when(passwordEncoder).encode(passwordCaptor.capture());

            // ACT
            Mockito.doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            Mockito.when(userRepository.findByEmail("lailsonbit@gmail.com")).thenReturn(null);
            var output = userService.saveUser(userRecordDTO);
            System.out.println(" OUTPUT " +  output);

            var userCaptured = userArgumentCaptor.getValue();
            System.out.println(" CAPTURED " +  userCaptured);

            // ASSERT
            assertEquals(output.getName() , userCaptured.getName());
            assertEquals(output.getEmail() , userCaptured.getEmail());
            assertEquals(output.getPassword() , userCaptured.getPassword());
            assertEquals(output.getRole() , userCaptured.getRole());
        }

        @Test
        void shouldReturnUserAlreadyExists(){

            var userRecordDTO = new UserDTO(2L , "Lailson" , "lailsonbit@gmail.com",
                    "lailson123" , "STUDENT" , 1L);

            var user = new User(2L, "Lailson" , "lailsonbit@gmail.com", "lailson123", LocalDateTime.now(),
                    UserRole.STUDENT, null , null , null);
            Mockito.when(userRepository.findByEmail("lailsonbit@gmail.com")).thenReturn(user);

            assertThrows(DuplicatedException.class, () -> userService.saveUser(userRecordDTO));
        }


    }


    @Test
    void getAllUsers() {
    }
}