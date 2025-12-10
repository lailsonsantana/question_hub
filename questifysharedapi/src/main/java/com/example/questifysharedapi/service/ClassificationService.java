package com.example.questifysharedapi.service;

import com.example.questifysharedapi.dto.ClassificationRecordDTO;
import com.example.questifysharedapi.model.Classification;
import com.example.questifysharedapi.model.Question;
import com.example.questifysharedapi.model.User;
import com.example.questifysharedapi.repository.ClassificationRepository;
import com.example.questifysharedapi.repository.QuestionRepository;
import com.example.questifysharedapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ClassificationService {

    private final ClassificationRepository classificationRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;


    @Transactional
    public Classification saveClassification(ClassificationRecordDTO classificationRecordDTO) {
        // Get the User and Question options
        Optional<Question> opQuestion = questionRepository.findById(classificationRecordDTO.questionId());
        Optional<User> opUser = userRepository.findById(classificationRecordDTO.userId());

        // Verify if both are presents
        if (opQuestion.isPresent() && opUser.isPresent()) {
            Question question = opQuestion.get();
            User user = opUser.get();

            // Try to access the existent classification
            Optional<Classification> existingClassification = classificationRepository.findByUserIdAndQuestionId(
                    user.getId(), question.getId());

            Classification classification;

            if (existingClassification.isPresent()) {
                // If already a classification update the note
                classification = existingClassification.get();
                classification.setRating(classificationRecordDTO.rating());
            } else {
                // Otherwise create a new classification
                classification = new Classification();
                classification.setRating(classificationRecordDTO.rating());
                classification.setQuestion(question);
                classification.setUser(user);
            }

            // Save the classification or update
            return classificationRepository.save(classification);
        } else {
            // Handle with the case of User or Question not exist
            throw new EntityNotFoundException("User or Question not found");
        }
    }

    @Transactional
    public Double getClassificationByUserAndQuestion(Long questionId , Long userId){
        Optional<Classification> existingClassification = classificationRepository.findByUserIdAndQuestionId(userId,questionId);

        if(existingClassification.isPresent()){
            Classification classification = existingClassification.get();
            return classification.getRating();
        }
        return 0.0; 
    }
}
