package com.springboot.book.jojoldu.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/** 공통 사용하는 ENTITY 클래스 */
@Getter
@MappedSuperclass   // JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들도 컬럼으로 인식하도록 함
@EntityListeners(AuditingEntityListener.class)  // BaseTimeEntity 클래스에 Auditing 기능 포함
public abstract class BaseTimeEntity {

    @CreatedDate    // 엔티티 생성되어 저장될 때 시간 자동저장
    private LocalDateTime createDate;   // LocalDateTime 날짜와 시간 정보 출력

    @LastModifiedDate   // 조회한 엔티티 값 변경시 시간 자동저장
    private LocalDateTime modifiedDate;
}
