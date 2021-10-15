package com.springboot.book.jojoldu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing  // JPA Auditing 활성화
@SpringBootApplication // 스프링 부트 자동설정, 스프링 Bean 읽기와 생성 자동 설정. 이 어노테이션의 위치부터 설정을 읽기 때문에 이 클래스는 항상 프로젝트의 최 상단에 위치해야만 합니다.
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // 내장 WAS 실 행
    }
}