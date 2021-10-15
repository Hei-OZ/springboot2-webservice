package com.springboot.book.jojoldu.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 게시글 업데이트 요청 DTO */
@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
