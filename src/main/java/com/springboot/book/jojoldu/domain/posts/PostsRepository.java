package com.springboot.book.jojoldu.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/** 게시글 - DB와 CLASS들을 연결해주는 클래스 */
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")  // Posts 클래스 select 해 오는 쿼리. Spring Data Jpa에서 제공하는 기본 메소드로도 해결 가능하나 @Query 어노테이션 사용법 시연을 위해 코딩
    List<Posts> findAllDesc();
}
