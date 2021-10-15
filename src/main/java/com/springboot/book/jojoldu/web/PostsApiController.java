package com.springboot.book.jojoldu.web;

import com.springboot.book.jojoldu.service.posts.PostsService;
import com.springboot.book.jojoldu.web.dto.PostsResponseDto;
import com.springboot.book.jojoldu.web.dto.PostsSaveRequestDto;
import com.springboot.book.jojoldu.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/** 게시글 API 컨트롤러 */
@RequiredArgsConstructor // 롬복. 선언된 모든 final 필드가 포함된 생성자 생성. final 없으면 포함 안 됨
@RestController
public class PostsApiController {


    private final PostsService postsService;

    // POST => INSERT 기능 => 등록
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    // PUT => UPDATE 기능 => 수정
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    // GET => SELECT 기능 => 조회
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    // DELETE => DELETE 기능 => 삭제
    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}
