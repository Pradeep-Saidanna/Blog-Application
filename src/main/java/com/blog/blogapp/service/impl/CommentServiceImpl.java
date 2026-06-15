package com.blog.blogapp.service.impl;

import com.blog.blogapp.entity.Comment;
import com.blog.blogapp.entity.Post;
import com.blog.blogapp.repository.CommentRepository;
import com.blog.blogapp.repository.PostRepository;
import com.blog.blogapp.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void addComment(Long postId, String name, String email, String comment) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment c = new Comment();
        c.setName(name);
        c.setEmail(email);
        c.setComment(comment);
        c.setPost(post);

        commentRepository.save(c);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    @Override
    public Comment updateComment(Long id, String name, String email, String comment) {

        Comment existing = getCommentById(id);

        existing.setName(name);
        existing.setEmail(email);
        existing.setComment(comment);

        return commentRepository.save(existing);
    }

    @Override
    public Long deleteComment(Long id, String email) {

        Comment comment = getCommentById(id);

        if (!comment.getEmail().equals(email)) {
            throw new RuntimeException("You are not allowed to delete this comment");
        }

        Long postId = comment.getPost().getId();

        commentRepository.delete(comment);

        return postId;
    }
}