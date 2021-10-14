package com.springboot.book.jojoldu.service.posts;

import com.springboot.book.jojoldu.domain.posts.Posts;
import com.springboot.book.jojoldu.domain.posts.PostsRepository;
import com.springboot.book.jojoldu.web.dto.PostsResponseDto;
import com.springboot.book.jojoldu.web.dto.PostsSaveRequestDto;
import com.springboot.book.jojoldu.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor    // 롬복. 선언된 모든 final 필드가 포함된 생성자 생성. final 없으면 포함 안 됨
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = "+ id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = "+ id));

        return new PostsResponseDto(entity);
    }
}
