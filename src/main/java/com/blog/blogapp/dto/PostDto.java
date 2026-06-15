package com.blog.blogapp.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PostDto {

    private Long id;
    private String title;
    private String excerpt;
    private String content;
    private String author;
    private LocalDateTime publishedAt;
    private Boolean published;
    private List<String> tags;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public Boolean getPublished() {
        return published;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}