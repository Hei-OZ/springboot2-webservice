package com.springboot.book.jojoldu.web.dto;

import com.springboot.book.jojoldu.domain.posts.Posts;
import javafx.geometry.Pos;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor  // 롬복. 기본생성자 자동 추가. public PostsSaveRequestDto(){} <= 추가 됨
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder    // 롬복. 해당 클래스의 빌더 패턴 클래스 생성.
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
