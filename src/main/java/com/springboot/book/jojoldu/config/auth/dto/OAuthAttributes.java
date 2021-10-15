package com.springboot.book.jojoldu.config.auth.dto;

import com.springboot.book.jojoldu.domain.user.Role;
import com.springboot.book.jojoldu.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/** 사용자 정보 없을 경우 OAuthAttributes에 값을 담아 save 시킨다 */
@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes; // OAuth2에서 받아온 정보들 OAuth2User.getAttributes()
    private String nameAttributeKey;    // userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName() = OAuth2 로그인 진행시 키가 되는 필드값. PK 같은 거
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    // 네이버인지 구글인지 분기 처리하는 메소드
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ( "naver".equals(registrationId) ) {
            return ofNaver("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    // 네이버일 경우 response 어트리뷰트 받아서 값 할당
    private static OAuthAttributes ofNaver(String userNameAttrivuteName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttrivuteName)
                .build();

    }

    // 구글일 경우 attributes 값 받아서 할당
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    // 신규 사용자일 경우 사용하는 toEntity() 메서드. 권한을 GUEST로 준다.
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
