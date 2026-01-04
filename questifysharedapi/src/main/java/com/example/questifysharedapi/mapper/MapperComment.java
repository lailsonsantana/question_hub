package com.example.questifysharedapi.mapper;

import com.example.questifysharedapi.dto.CommentDTO;
import com.example.questifysharedapi.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MapperComment {

    @Mappings({
            @Mapping(source = "user.name", target = "nameUser"),
            @Mapping(source = "createdAt", target = "createdAt", dateFormat = "dd/MM/yyyy")
    })
    CommentDTO toCommentDTO(Comment comment);

    List<CommentDTO> toCommentsDTO(List<Comment> comments);

}
