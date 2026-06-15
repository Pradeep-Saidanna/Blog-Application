package com.blog.blogapp.security;

import com.blog.blogapp.repository.PostRepository;
import org.springframework.stereotype.Component;

@Component("postSecurity")
public class PostSecurity {

    private final PostRepository postRepository;

    public PostSecurity(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public boolean isOwner(Long postId, String email) {

        return postRepository.findById(postId)
                .map(post ->
                        post.getUser() != null &&
                                post.getUser().getEmail().equals(email))
                .orElse(false);
    }
}