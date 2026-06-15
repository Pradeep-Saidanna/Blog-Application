package com.blog.blogapp.mapper;

import com.blog.blogapp.dto.CommentDto;
import com.blog.blogapp.entity.Comment;

public class CommentMapper {

    public static CommentDto toDto(Comment comment) {

        CommentDto dto = new CommentDto();

        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setComment(comment.getComment());
        dto.setPostId(comment.getPost().getId());
        dto.setCreatedAt(comment.getCreatedAt());

        return dto;
    }
}