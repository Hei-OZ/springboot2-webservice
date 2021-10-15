package com.springboot.book.jojoldu.domain.user;

import com.springboot.book.jojoldu.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/** 유저 도메인 Entity 클래스 - 테이블과 매칭되는 클래스 */
@Getter
@NoArgsConstructor  // 빈 생성자 생성
@Entity // 엔티티 클래스임을 선언
public class User extends BaseTimeEntity {

    @Id // PK 선언
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)    // JPA로 데이터베이스 저장시 Enum 값을 어떤 형태로 저장할지 결정. 기본적으로 int지만 알아보기 쉽게 문자열로 저장하도록 선언
    @Column(nullable = false)
    private Role role;  // Role.java 라는 Enum 클래스에 권한 Enum 값 선언

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
