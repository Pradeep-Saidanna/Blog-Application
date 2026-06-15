package com.blog.blogapp.mapper;

import com.blog.blogapp.dto.PostDto;
import com.blog.blogapp.entity.Post;

import java.util.stream.Collectors;

public class PostMapper {

    // ENTITY → DTO
    public static PostDto toDto(Post post) {

        PostDto dto = new PostDto();

        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setExcerpt(post.getExcerpt());
        dto.setAuthor(post.getAuthor());
        dto.setPublishedAt(post.getPublishedAt());
        dto.setPublished(post.getPublished());

        if (post.getTags() != null) {
            dto.setTags(
                    post.getTags()
                            .stream()
                            .map(tag -> tag.getName())
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}