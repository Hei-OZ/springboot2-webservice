package com.springboot.book.jojoldu.web;

import com.springboot.book.jojoldu.domain.posts.Posts;
import com.springboot.book.jojoldu.domain.posts.PostsRepository;
import com.springboot.book.jojoldu.web.dto.PostsSaveRequestDto;
import com.springboot.book.jojoldu.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 랜덤 포트 사용
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;  // 서블릿 컨테이너 직접 실행하여 테스트. MockTest는 서블릿 컨테이너 실행하지 않음.

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception {
        // given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                                        .title(title)
                                        .content(content)
                                        .author("author")
                                        .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
        // ResponseEntity => HttpStatus, HttpHeaders, HttpBody 포함
        // Spring Framework에서 제공하는 HttpEntity(HTTP요청-Request, 응답-response 에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스)를 상속받아 구현한 클래스가 RequestEntity와 ResponseEntity
        // postForEntity POST 요청을 보내고 결과를 ResponseEntity로 반환 받는다
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L); // 받아온 값이 0L보다 큰 값인지 비교

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts_수정된다() throws Exception {
        // given - 데이터 저장부터
        Posts savedPosts = postsRepository.save(Posts.builder()
                                                .title("title")
                                                .content("content")
                                                .author("author")
                                                .build());

        Long updateId = savedPosts.getId(); // PK 값
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                                            .title(expectedTitle)
                                            .content(expectedContent)
                                            .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        // Spring Framework에서 제공하는 HttpEntity(HTTP요청-Request, 응답-response 에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스)
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        // exchange -> HTTP 헤더를 새로 만들 수 있고, 어떤 HTTP method에서도 사용 가능하다.
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}