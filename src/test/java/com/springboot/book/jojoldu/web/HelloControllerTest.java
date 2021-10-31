package com.springboot.book.jojoldu.web;

import com.springboot.book.jojoldu.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)    // 테스트 진행시 JUnit에 내장된 실행자 외 다른 실행자를 실행시킴. 여기서는 SpringExtension라는 스프링 실행자를 사용. 즉 스프링부트 테스트와 JUnit 사이의 연결자 역할
@WebMvcTest(controllers = HelloController.class,    // WebMvcTest 는 Component를 스캔하지 않으므로 스프링 시큐리티 설정 SecurityConfig 를 생성하기 위한 CustomOAuth2UserService 읽을 수 없어 에러 발생
    excludeFilters = {  // SecurityConfig.class 스캔하지 않도록 필터링
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
    }
)   // @WebMvcTest => Web(Spring MVC)에 집중할 수 있는 어노테이션. @Controller, @ControllerAdvice 사용가능. @Service, @Component, @Repository 사용 불가. 이 소스에서는 Controller만 사용하기에 선언
public class HelloControllerTest {  // @WebMvcTest는 JPA 작동하지 않음. JPA 테스트시 @SpringBootTest 어노테이션 이용

    @Autowired  // 스프링이 관리하는 Bean 자동 주입
    private MockMvc mvc;    // 웹 API 테스트시 사용. 스프링 MVC 테스트의 시작점. 이 클래스를 통하여 HTTP GET, POST 등에 대한 API 테스트 가능. 서블릿 컨테이너 실행하지 않음.

    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))  // MockMvc를 통해 /hello 주소로 HTTP GET 요청. 체이닝 지원으로 아래 구문처럼 이어서 검증 선언 가능
                .andExpect(status().isOk()) // 결과 검증. HTTP Header의 Status(200, 404, 500등) 검증. 여기서는 OK = 200인지 아닌지 검증
                .andExpect(content().string(hello));    // 결과 검증. 응답 본문 내용 검증. Controller에서 "hello"라는 문자열을 리턴하기 때문에 이 값이 맞는지 검증

    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name)    // API 테스트시 요청 파라미터.
                .param("amount", String.valueOf(amount)))   // 값은 String만 허용하므로 숫자/날짜 데이터 전부 문자열 변경해야 함
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))    // JSON 응답값을 필드별로 검증할 수 있는 메소드. $을 기준으로 필드명 명시.
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
