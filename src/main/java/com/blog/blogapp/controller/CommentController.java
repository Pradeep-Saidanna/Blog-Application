package com.blog.blogapp.controller;

import com.blog.blogapp.entity.Comment;
import com.blog.blogapp.entity.Post;
import com.blog.blogapp.service.CommentService;
import com.blog.blogapp.service.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    public CommentController(CommentService commentService,
                             PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("/save")
    public String saveComment(@RequestParam Long postId,
                              @RequestParam String name,
                              @RequestParam String email,
                              @RequestParam String comment) {

        commentService.addComment(postId, name, email, comment);

        return "redirect:/posts/" + postId;
    }

    @GetMapping("/edit/{id}")
    public String editComment(@PathVariable Long id,
                              Model model) {

        Comment existing = commentService.getCommentById(id);

        model.addAttribute("comment", existing);

        return "edit-comment";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update")
    public String updateComment(@RequestParam Long id,
                                @RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String comment) {

        Comment updated = commentService.updateComment(id, name, email, comment);

        return "redirect:/posts/" + updated.getPost().getId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id,
                                Authentication authentication) {

        String email = authentication.getName();

        Long postId = commentService.deleteComment(id, email);

        return "redirect:/posts/" + postId;
    }
}