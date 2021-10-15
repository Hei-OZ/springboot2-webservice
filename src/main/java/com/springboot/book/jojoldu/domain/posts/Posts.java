package com.springboot.book.jojoldu.domain.posts;

import com.springboot.book.jojoldu.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/** 게시글 도메인 Entity 클래스 - 테이블과 매칭되는 클래스 */
// 코딩시 주요 어노테이션을 클래스에 가깝게 둔다. 롬복이 더이상 필요없을 경우 걷어내기 쉽다.
@Getter
@NoArgsConstructor  // 롬복 어노테이션. 기본생성자 자동 추가. public Posts(){} <= 추가 됨
@Entity // JPA 어노테이션. 실제 DB와 매칭 될 클래스로 지정. 클래스=카멜케이스 네이밍 / 테이블=언더스코어 네이밍
public class Posts extends BaseTimeEntity {    // 실제 DB와 매칭 될 클래스는 Entity 클래스라고도 부름

    @Id // 이 테이블의 PK 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // JPA 어노테이션. PK 생성규칙. GenerationType.IDENTITY = auto_increment (스프링부트 2.0)
    private Long id;    // long으로 설정하면 MySQL의 경우 bigint가 된다.

    @Column(length = 500, nullable = false) // JPA 어노테이션. 변경사항 있을 경우 사용하는 어노테이션. 문자열 varchar(255)가 기본값인데 이 라인에서는 500으로 늘림
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)    // 문자열 타입을 varchar(255)에서 TEXT로 변경
    private String content;

    private String author;  // 기본 설정이므로 varchar(255), null 허용

    @Builder    // 롬복 어노테이션. 해당 클래스의 빌더 패턴 클래스 생성. 생성자 상단에 선언시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
