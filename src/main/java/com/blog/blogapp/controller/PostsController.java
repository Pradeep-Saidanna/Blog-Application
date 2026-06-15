package com.blog.blogapp.controller;

import com.blog.blogapp.entity.Comment;
import com.blog.blogapp.entity.Post;
import com.blog.blogapp.exception.PostNotFoundException;
import com.blog.blogapp.repository.CommentRepository;
import com.blog.blogapp.service.CommentService;
import com.blog.blogapp.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostsController {

    private final PostService postService;
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    public PostsController(PostService postService,
                           CommentRepository commentRepository,
                           CommentService commentService) {

        this.postService = postService;
        this.commentRepository = commentRepository;
        this.commentService = commentService;
    }

    @GetMapping("/posts")
    public String getPosts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Long tagId,
            @RequestParam(defaultValue = "publishedAt") String sortField,
            @RequestParam(defaultValue = "desc") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Sort sort = order.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> postPage;

        if (search != null && !search.isEmpty()) {
            postPage = postService.searchPosts(search, pageable);
            model.addAttribute("search", search);

        } else if (author != null && !author.isEmpty()) {
            postPage = postService.getPostsByAuthor(author, pageable);
            model.addAttribute("author", author);

        } else if (tagId != null) {
            postPage = postService.getPostByTag(tagId, pageable);
            model.addAttribute("tagId", tagId);

        } else {
            postPage = postService.getPosts(pageable);
        }

        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("order", order);

        return "posts";
    }

    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable Long id, Model model) {

        Post post = postService.getPostById(id);

        model.addAttribute("post", post);
        model.addAttribute("comments",
                commentRepository.findByPostId(id));

        return "post-details";
    }

    @PreAuthorize("hasRole('ADMIN') or @postSecurity.isOwner(#id, authentication.name)")
    @GetMapping("/posts/edit/{id}")
    public String editPost(@PathVariable Long id, Model model) {

        model.addAttribute("post", postService.getPostById(id));

        return "edit-post";
    }

    @PreAuthorize(
            "hasRole('ADMIN') or @postSecurity.isOwner(#post.id, authentication.name)"
    )
    @PostMapping("/posts/update")
    public String updatePost(@Valid @ModelAttribute Post post,
                             BindingResult result,
                             @RequestParam(required = false) String tagInput) {

        if (result.hasErrors()) {
            return "edit-post";
        }

        postService.updatePostWithTags(post,
                tagInput != null ? tagInput : "");

        return "redirect:/posts";
    }

    @PreAuthorize("hasRole('ADMIN') or @postSecurity.isOwner(#id, authentication.name)")
    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Long id) {

        postService.deletePost(id);

        return "redirect:/posts";
    }

    @GetMapping("/posts/new")
    public String createPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "create-post";
    }

    @PostMapping("/posts/save")
    public String savePost(@Valid @ModelAttribute Post post,
                           BindingResult result,
                           @RequestParam(required = false) String tagInput) {

        if (result.hasErrors()) {
            return "create-post";
        }

        postService.savePostWithTags(post, tagInput);

        return "redirect:/posts";
    }

}