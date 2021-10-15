package com.springboot.book.jojoldu.config.auth;

import com.springboot.book.jojoldu.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component  // @Component 어노테이션을 붙여 빈 설정 파일이 아닌 빈 클래스에서 빈 직접 등록
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    // HandlerMethodArgumentResolver 조건에 맞는 메소드가 있는 경우, 구현체가 지정한 값을 해당 메소드에 파라미터로 넘길 수 있음(원하는 값 바인딩 가능)
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {   // 컨트롤러 메서드의 특정 파라미터 지원 여부 판단
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;  // @LoginUser 어노테이션이 붙어있는지 판단
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());   // parameter 클래스타입이 SessionUser.class 인지 판단

        return isLoginUserAnnotation && isUserClass;
    }

    // 위 메서드 supportsParameter 가 true 라면 resolveArgument 메서드가 값을 바인딩 해 준다
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 파라미터에 전달될 객체 생성
        return httpSession.getAttribute("user");    // 세션에서 객체를 가지고 와서 넘겨 줌
    }
}
