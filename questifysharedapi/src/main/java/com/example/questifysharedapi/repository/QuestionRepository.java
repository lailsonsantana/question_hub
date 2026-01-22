package com.example.questifysharedapi.repository;

import com.example.questifysharedapi.model.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuestionRepository extends JpaRepository<Question,Long> {

    List<Question> findAllByDiscipline(String name);
    List<Question> findAllByUser_id(Long userId);
    List<Question> findAllByOrderByIdAsc();

}
