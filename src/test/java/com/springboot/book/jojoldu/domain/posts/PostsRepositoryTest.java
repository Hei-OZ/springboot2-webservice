package com.springboot.book.jojoldu.domain.posts;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest // 별다른 설정 없이도 H2데이터베이스 자동 실행해주는 어노테이션.
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach  // JUnit 에서 단위 테스트가 끝날때마다 수행 할 메소드를 지정하는 어노테이션.
    public void cleanup() {
        postsRepository.deleteAll();    // deleteAll() = 테이블 posts의 모든 데이터 삭제 메소드
    }

    @Test
    public void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()    // save 메소드. 테이블 posts에 insert/update 쿼리 실행. id값이 있다면 update, 없다면 insert
                .title(title)
                .content(content)
                .author("jojoldu@gmail.com")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();  // findAll() = 테이블 posts의 모든 데이터를 조회 해 오는 메소드

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);  // 2019년 6월 4일 0시 0분 0초
        postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        System.out.println(">>>>>>> createDate=" + posts.getCreateDate() + ", modifiedDate=" + posts.getModifiedDate());

        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);   // isAfter(비교할 값) 날짜/시간이 비교할 값보다 이후인지 검증
    }
}