package com.springboot.book.jojoldu.web.dto;

import com.springboot.book.jojoldu.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

/** 게시글 LIST 응답 DTO */
@Getter
public class PostsListResponseDto {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}
