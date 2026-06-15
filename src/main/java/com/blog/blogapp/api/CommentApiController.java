package com.blog.blogapp.api;

import com.blog.blogapp.dto.CommentDto;
import com.blog.blogapp.mapper.CommentMapper;
import com.blog.blogapp.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentApiController {

    private final CommentService commentService;

    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    // ADD COMMENT
    @PostMapping
    public ApiResponse<String> add(@RequestParam Long postId,
                                   @RequestParam String name,
                                   @RequestParam String email,
                                   @RequestParam String comment) {

        commentService.addComment(postId, name, email, comment);

        return ApiResponse.success("Comment added", null);
    }

    // GET COMMENT
    @GetMapping("/{id}")
    public ApiResponse<CommentDto> get(@PathVariable Long id) {

        return ApiResponse.success(
                CommentMapper.toDto(commentService.getCommentById(id))
        );
    }

    // UPDATE COMMENT
    @PutMapping("/{id}")
    public ApiResponse<CommentDto> update(@PathVariable Long id,
                                          @RequestParam String name,
                                          @RequestParam String email,
                                          @RequestParam String comment) {

        return ApiResponse.success(
                CommentMapper.toDto(
                        commentService.updateComment(id, name, email, comment)
                )
        );
    }

    // DELETE COMMENT
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id,
                                      @RequestParam String email) {

        Long postId = commentService.deleteComment(id, email);

        return ApiResponse.success("Deleted", "Post ID: " + postId);
    }
}