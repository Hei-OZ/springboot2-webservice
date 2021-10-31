package com.springboot.book.jojoldu.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        // 현재 실행중인 모든 ActiveProfile 가져 옴
        List<String> profiles = Arrays.asList(env.getActiveProfiles());

        List<String> realProfiles = Arrays.asList("real", "real1", "real2");

        String defaultProfile = profiles.isEmpty()? "default" : profiles.get(0);

        return profiles.stream()
                .filter(realProfiles::contains) // 모든 ActiveProfile 중 realProfiles 와 일치하는 문자열 있는지
                .findAny()  // 가장 먼저 찾은 요소 리턴. findFirst()는 찾은것 중 가장 앞 요소 리턴
                .orElse(defaultProfile);    // 값이 있다면 반환, 없다면 defaultProfile 반환
    }
}
