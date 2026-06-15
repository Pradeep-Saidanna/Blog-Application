package com.blog.blogapp.api;

import com.blog.blogapp.dto.PostDto;
import com.blog.blogapp.entity.Post;
import com.blog.blogapp.mapper.PostMapper;
import com.blog.blogapp.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostApiController {

    private final PostService postService;

    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ApiResponse<Page<PostDto>> getAll(Pageable pageable) {

        Page<PostDto> result =
                postService.getPosts(pageable)
                        .map(PostMapper::toDto);

        return ApiResponse.success(result);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDto> getById(@PathVariable Long id) {

        PostDto dto =
                PostMapper.toDto(
                        postService.getPostById(id)
                );

        return ApiResponse.success(dto);
    }

    @GetMapping("/search")
    public ApiResponse<Page<PostDto>> search(
            @RequestParam String keyword,
            Pageable pageable) {

        Page<PostDto> result =
                postService.searchPosts(keyword, pageable)
                        .map(PostMapper::toDto);

        return ApiResponse.success(result);
    }

    @PostMapping
    public ApiResponse<PostDto> create(
            @RequestBody PostDto dto,
            @RequestParam(required = false) String tags) {

        Post post = new Post();

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setExcerpt(dto.getExcerpt());
        post.setPublished(dto.getPublished());

        Post saved =
                postService.savePostWithTags(post, tags);

        return ApiResponse.success(
                "Post created",
                PostMapper.toDto(saved)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<PostDto> update(
            @PathVariable Long id,
            @RequestBody PostDto dto,
            @RequestParam(required = false) String tags) {

        Post post =
                postService.getPostById(id);

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setExcerpt(dto.getExcerpt());
        post.setPublished(dto.getPublished());

        Post updated =
                postService.updatePostWithTags(post, tags);

        return ApiResponse.success(
                "Post updated",
                PostMapper.toDto(updated)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(
            @PathVariable Long id) {

        postService.deletePost(id);

        return ApiResponse.success(
                "Post deleted",
                "Deleted ID : " + id
        );
    }
}