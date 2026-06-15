package com.blog.blogapp.repository;

import com.blog.blogapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long>{
    Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrExcerptContainingIgnoreCase(
            String title,
            String content,
            String author,
            String excerpt,
            Pageable pageable
    );
    Page<Post> findByAuthorIgnoreCase(String author, Pageable pageable);
    Page<Post> findByTags_NameContainingIgnoreCase(String tag, Pageable pageable);
    Page<Post> findByTags_Id(Long tagId, Pageable pageable);
    @Query("""
       Select Distinct p
       from Post p
       Left Join p.tags t
       where
       Lower(p.title) Like Lower(Concat('%', :keyword, '%'))
       Or Lower(p.content) Like Lower(Concat('%', :keyword, '%'))
       or Lower(p.author) like Lower(Concat('%', :keyword, '%'))
       or Lower(p.excerpt) Like Lower(Concat ('%', :keyword, '%'))
       or Lower(t.name) Like Lower(Concat('%', :keyword, '%'))
    """)
    Page<Post> searchPostsIncludingTags(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
