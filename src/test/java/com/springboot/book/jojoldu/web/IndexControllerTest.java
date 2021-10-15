package com.springboot.book.jojoldu.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void 메인페이지_로딩() {
        //when
        String body = this.restTemplate.getForObject("/", String.class);    // getForObject : 주어진 url 주소로 HTTP GET메서드 객체로 결과를 반환 받는다.

        System.out.println(">>>>>>>>> body : " + body);
        /* >>>>>>>>> body : <!doctype html>
         * <html lang="ko">
         * <head>
         *     <meta charset="UTF-8">
         *     <meta name="viewport"
         *           content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
         *     <meta http-equiv="X-UA-Compatible" content="ie=edge">
         *     <title>스프링 부트 웹서비스</title>
         * </head>
         * <body>
         *     <h1>스프링 부트로 시작하는 웹 서비스</h1>
         * </body>
         * </html>
         */

        //then
        assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");    // contains(인자) : 인자로 지정한 문자열들을 모두 포함하고 있는지 검증한다.
    }
}