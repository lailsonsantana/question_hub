package com.example.questifysharedapi.service;

import com.example.questifysharedapi.dto.ContextRecordDTO;
import com.example.questifysharedapi.exception.ContextNotFound;
import com.example.questifysharedapi.model.Context;
import com.example.questifysharedapi.repository.ContextRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class ContextService {

    private ContextRepository contextRepository;

    public Context saveContext(ContextRecordDTO newContext) {
        Context context = new Context();
        context.setText(newContext.text());
        return contextRepository.save(context);
    }

    public Context getContext(Long id){
        Optional<Context> opContext = contextRepository.findById(id);
        if(opContext.isPresent()){
            return opContext.get();
        }
        throw new ContextNotFound("Context Not Found");

    }

    public List<Context> getAllContexts(){

        return contextRepository.findAll();
    }
}

