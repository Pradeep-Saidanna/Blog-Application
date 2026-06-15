package com.blog.blogapp.service.impl;


import com.blog.blogapp.entity.Post;
import com.blog.blogapp.exception.PostNotFoundException;
import com.blog.blogapp.repository.PostRepository;
import com.blog.blogapp.repository.TagRepository;
import com.blog.blogapp.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.blog.blogapp.entity.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.blog.blogapp.entity.User;
import com.blog.blogapp.repository.UserRepository;


import java.util.ArrayList;
import java.util.List;

//import static jdk.internal.jrtfs.JrtFileAttributeView.AttrID.size;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, TagRepository tagRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<Post> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with given id"));
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Page<Post> searchPosts(String keyword, Pageable pageable) {
        return postRepository.searchPostsIncludingTags(
            keyword, pageable);
    }

    @Override
    public Page<Post> getPostsByAuthor(String author, Pageable pageable) {
        return postRepository.findByAuthorIgnoreCase(author, pageable);
    }

    @Override
    public Page<Post> getPostByTagName(String tag, Pageable pageable) {
        return postRepository.findByTags_NameContainingIgnoreCase(tag, pageable);
    }

    @Override
    public Post savePostWithTags(Post post, String tagInput) {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();
        System.out.println("Logged User Email = " + email);

        User user =
                userRepository.findByEmail(email);
        System.out.println("User Found = " + user);

        post.setUser(user);

        post.setAuthor(user.getName());

        post.setTags(convertToTags(tagInput));

        return postRepository.save(post);
    }

    @Override
    public Post updatePostWithTags(Post post, String tagInput) {
        Post existing = postRepository.findById(post.getId())
                .orElseThrow(() -> new PostNotFoundException("Post not Found with given id"));
        existing.setTitle(post.getTitle());
        existing.setAuthor(post.getAuthor());
        existing.setContent(post.getContent());
        existing.setExcerpt(post.getExcerpt());
        existing.setPublished(post.getPublished());
        existing.setPublishedAt(post.getPublishedAt());
        if(existing.getTags() != null) {
            existing.getTags().clear();
        }
        existing.setTags(convertToTags(tagInput));
        return postRepository.save(existing);
    }

    private List<Tag> convertToTags(String tagInput) {
        List<Tag> tags = new ArrayList<>();
        if(tagInput != null && !tagInput.isEmpty()) {
            String [] tagNames = tagInput.split(",");
            for(String name : tagNames) {
                String trimmed = name.trim();
                if(trimmed.isEmpty()) continue;
                Tag existingTag = tagRepository.findByName(trimmed);
                if(existingTag != null) {
                    tags.add(existingTag);
                } else {
                    Tag newTag = new Tag();
                    newTag.setName(trimmed);
                    tags.add(newTag);
                }
            }
        }
        return tags;
    }

    @Override
    public boolean isPostOwner(Long postId, String email) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new PostNotFoundException(
                                "Post not found"));

        return post.getUser() != null &&
                post.getUser()
                        .getEmail()
                        .equals(email);
    }

    @Override
    public boolean isAdmin(String email) {

        User user =
                userRepository.findByEmail(email);

        return user != null &&
                "ROLE_ADMIN".equals(user.getRole());
    }
    @Override
    public Page<Post> getPostByTag(Long tagId, Pageable pageable) {
        return postRepository.findByTags_Id(tagId, pageable);
    }
}