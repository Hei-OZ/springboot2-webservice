package com.springboot.book.jojoldu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration  // 설정 파일을 만들기 위한 어노테이션 or Bean을 등록하기 위한 어노테이션. Bean 등록시 싱글톤(singleton)이 되도록 보장. 스프링 컨테이너에서 Bean을 관리할 수 있게 됨.
@EnableJpaAuditing  // JPA Auditing 활성화.
public class JpaConfig {
}
/* Junit의 @WebMvcTest 는 Entity를 필요로 하지 않는다. 그런데 @EnableJpaAuditing을 사용하려면 Entity를 필요로 한다.
    Application.java에 전역 설정 되어 있어 에러 발생하므로 @EnableJpaAuditing 설정을 따로 빼 주었다. */
