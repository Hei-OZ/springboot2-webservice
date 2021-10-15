package com.springboot.book.jojoldu.config;

import com.springboot.book.jojoldu.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration  // 설정 파일을 만들기 위한 어노테이션 or Bean을 등록하기 위한 어노테이션. Bean 등록시 싱글톤(singleton)이 되도록 보장. 스프링 컨테이너에서 Bean을 관리할 수 있게 됨.
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    // 값 바인딩 해 줌. HandlerMethodArgumentResolver 는 항상 WebMvcConfigurer 의 addArgumentResolvers 를 통해 값을 바인딩 해야 함
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }
}
