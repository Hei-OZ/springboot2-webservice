package com.springboot.book.jojoldu.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)  // 이 어노테이션이 생성될 수 있는 위치 지정. 메소드의 PARAMETER 로 선언된 객체에서만 사용 가능
@Retention(RetentionPolicy.RUNTIME) // 커스텀 어노테이션 설정시 사용. 어느 시점까지 어노테이션 메모리를 가져갈 지 설정함. enum RetentionPolicy - 메모리 보유 범위.
public @interface LoginUser {   // @interface 어노테이션 클래스로 지정함. @LoginUser 가 생성된 것
}
