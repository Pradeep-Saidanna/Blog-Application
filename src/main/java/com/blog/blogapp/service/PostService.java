package com.blog.blogapp.service;

import com.blog.blogapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostService {
    Page<Post> getPosts(Pageable pageable);
    Post getPostById(Long id);
    void deletePost(Long id);
    Page<Post> searchPosts(String keyword, Pageable pageable);
    Page<Post> getPostsByAuthor(String author, Pageable pageable);
    Post savePostWithTags(Post post, String tagInput);
    Post updatePostWithTags(Post post, String tagInput);
    Page<Post> getPostByTagName(String tag, Pageable pageable);
    boolean isPostOwner(Long postId, String email);
    boolean isAdmin(String email);
    Page<Post> getPostByTag(Long tagId, Pageable pageable);
}
