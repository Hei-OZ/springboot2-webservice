package com.springboot.book.jojoldu.service.posts;

import com.springboot.book.jojoldu.domain.posts.Posts;
import com.springboot.book.jojoldu.domain.posts.PostsRepository;
import com.springboot.book.jojoldu.web.dto.PostsListResponseDto;
import com.springboot.book.jojoldu.web.dto.PostsResponseDto;
import com.springboot.book.jojoldu.web.dto.PostsSaveRequestDto;
import com.springboot.book.jojoldu.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/** 게시글 서비스 순서 지정 SERVICE */
@RequiredArgsConstructor    // 롬복. 선언된 모든 final 필드가 포함된 생성자 생성. final 없으면 포함 안 됨
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    // 저장
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    // 수정
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = "+ id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    // 글 1개 불러오기
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = "+ id));

        return new PostsResponseDto(entity);
    }

    // 글 리스트 불러오기
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)     // .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());      // .collect(형식); 받아온 스트림을 List 혹은 Set 형식으로 변경 해 줌
    }

    // 글 삭제하기
    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        postsRepository.delete(posts);
        //postsRepository.deleteById(posts.getId());
    }
}
