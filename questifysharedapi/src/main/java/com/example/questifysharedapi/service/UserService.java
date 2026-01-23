package com.example.questifysharedapi.service;

import com.example.questifysharedapi.dto.UserDTO;
import com.example.questifysharedapi.exception.DuplicatedException;
import com.example.questifysharedapi.exception.UserNotFound;
import com.example.questifysharedapi.mapper.MapperUser;
import com.example.questifysharedapi.model.AccessToken;
import com.example.questifysharedapi.model.User;
import com.example.questifysharedapi.model.UserRole;
import com.example.questifysharedapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MapperUser mapperUser;
    private final JwtService jwtService;

    @Transactional
    public UserDTO saveUser(UserDTO userDTO) {

        var possibleUser = userRepository.findByEmail(userDTO.email());

        if(possibleUser != null){
            throw new DuplicatedException("User already exists!");
        }
        User user = mapperUser.toUser(userDTO);
        encodePassword(user);

        return mapperUser.toUserDTO(userRepository.save(user));
    }

    public void deleteById(Long id){
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
        }
        throw new UserNotFound("User wasn't found");
    }

    public List<UserDTO> getAllUsers(){

        return mapperUser.toUsersDTO(userRepository.findAll());
    }

    public User getByEmail(String email) {
        System.out.println(userRepository.findByEmail(email).toString());
        return userRepository.findByEmail(email);
    }

    /*@Transactional
    public AccessToken authenticate(String email, String password) {
        // Verify if exists any user registered with this email
        var user = getByEmail(email);

        // Verify if the typed password is equals the password that is in the database
        if(user != null && passwordEncoder.matches(password , user.getPassword())){
            return jwtService.generateToken(user);
        }
        return null;
    }*/

    @Transactional
    public AccessToken authenticate(String email, String password) {
        // Verify if exists any user registered with this email
        System.out.println("ENTREI NO AUTENTICATE");
        var user = getByEmail(email);
        System.out.println(user);
        if(user == null){
            System.out.println("USUARIO NULO");
            return null;
        }
        System.out.println("PASSEI DO IF");
        // Verify if the typed password is equals the password that is in the database
        boolean match = passwordEncoder.matches(password ,user.getPassword());

        if(match){
            log.info("TEMOS UM MATCH");
            return jwtService.generateToken(user);
        }
        log.info("Retorne nulo");
        return null;
    }

    private void encodePassword(User user){
        String originalPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(originalPassword);
        user.setPassword(encodedPassword);
    }
}
