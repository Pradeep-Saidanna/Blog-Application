package com.blog.blogapp.service;

import com.blog.blogapp.entity.Comment;

public interface CommentService {

    void addComment(Long postId, String name, String email, String comment);

    Comment getCommentById(Long id);

    Comment updateComment(Long id, String name, String email, String comment);

    Long deleteComment(Long id, String email);
}