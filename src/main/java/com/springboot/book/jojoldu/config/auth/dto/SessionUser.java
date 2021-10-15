package com.springboot.book.jojoldu.config.auth.dto;

import com.springboot.book.jojoldu.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {  //
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
/* 세션 저장소를 WAS로 사용할 때는 문제 없으나, DB로 교체하면 객체 직렬화 에러가 발생한다. 그럴 경우 Serializable 를 implements 해 준다.
There was an unexpected error (type=Internal Server Error, status=500).
Failed to convert from type [java.lang.Object] to type [byte[]] for value 'com.springboot.book.jojoldu.config.auth.dto.SessionUser@69e215f6';
nested exception is org.springframework.core.serializer.support.SerializationFailedException: Failed to serialize object using DefaultSerializer;
nested exception is java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable payload but received an object of type [com.springboot.book.jojoldu.config.auth.dto.SessionUser]
 */
