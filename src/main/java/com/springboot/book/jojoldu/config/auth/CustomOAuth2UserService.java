package com.springboot.book.jojoldu.config.auth;

import com.springboot.book.jojoldu.config.auth.dto.OAuthAttributes;
import com.springboot.book.jojoldu.config.auth.dto.SessionUser;
import com.springboot.book.jojoldu.domain.user.User;
import com.springboot.book.jojoldu.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/** 소셜 로그인 서비스(순서지정) **/
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();    // 구글인지 네이버인지 구분하는 코드
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        // OAuth2 로그인 진행시 키가 되는 필드값. PK 같은 거
        // ↪ 구글 기본 코드는 sub, 네이버 카카오는 지원하지 않음.

        // OAuth2UserService로 가지고 온 OAuth2User 의 attribute를 담을 클래스. 구글/네이버등 공통 사용
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);   // email로 사용자 조회하여 값 있는지 없는지 판단 후 save or update
        httpSession.setAttribute("user", new SessionUser(user));    // 세션에 사용될 사용자 정보를 저장할 DTO. User 클래스를 사용하지 않고 새로 만들어 사용 함
        // 직렬화 하지 않을 경우 에러 발생하는데 User.java 는 엔티티 클래스로 직렬화 구현하지 않았기에, 신규 클래스 만들어 직렬화 하여 사용한다.

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
            // Collections.singleton 단 한 개의 객체만 저장 가능한 컬렉션 만들 때 사용
    }

    /** 기존 사용자가 있는데 사용자 이름, 프로필사진등이 변경되면 update 일어남
     * 기존 사용자가 없으면 save 일어남 */
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }


}
