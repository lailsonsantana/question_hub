package com.example.questifysharedapi.mapper;

import com.example.questifysharedapi.dto.UserDTO;
import com.example.questifysharedapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MapperUser {


    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO);
}
