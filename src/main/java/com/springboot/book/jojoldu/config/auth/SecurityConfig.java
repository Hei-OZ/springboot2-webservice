package com.springboot.book.jojoldu.config.auth;

import com.springboot.book.jojoldu.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity      // 스프링 시큐리티 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }

    /* .csrf().disable().headers().frameOptions().disable() h2 콘솔 화면 이용을 위해 해당 옵션 disable
         .authorizeRequests() // URL별 권한 설정 옵션. URL, HTTP 메소드별로 관리 가능
               .permitAll() // 해당 url은 전체 열람 권한
               .hasRole(Role.USER.name())   // 해당 url은 Role USER 권한을 가진 사람만 권한을 가짐
               .anyRequest().authenticated()    // 설정 이외 나머지 url들의 권한은 인증된 사용자(로그인 한 사용자)만 접근 가능
          .logout().logoutSuccessUrl("/")   // 로그아웃 성공시 / 주소로 이동
          .oauth2Login() // OAuth2 로그인 기능에 대한 설정 진입점
          .userInfoEndpoint()   // OAuth2 로그인 성공 이후 사용자 정보 가져올 때 설정 담당
          .userService(customOAuth2UserService);    // 로그인 성공시 후속조치 담당할 인터페이스 구현체 등록. 이 인터페이스에서 소셜서비스(리소스 서버)에서 사용자 정보를 가져온 상태에서 추가적으로 진행하고자 하는 기능 명시
     */
}
