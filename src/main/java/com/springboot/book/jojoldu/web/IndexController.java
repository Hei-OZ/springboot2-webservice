package com.springboot.book.jojoldu.web;

import com.springboot.book.jojoldu.config.auth.LoginUser;
import com.springboot.book.jojoldu.config.auth.dto.SessionUser;
import com.springboot.book.jojoldu.service.posts.PostsService;
import com.springboot.book.jojoldu.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

/** 화면단 컨트롤러 */
@RequiredArgsConstructor    // 롬복. 선언된 모든 final 필드가 포함된 생성자 생성. final 없으면 포함 안 됨
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;
    // 첫 화면. 글 리스트
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        //SessionUser user = (SessionUser) httpSession.getAttribute("user");  // 로그인 성공하여 sessionUser 값이 있다면
        // ↪ @LoginUser SessionUser user 어노테이션으로 교체
        if ( user != null ) {
            model.addAttribute("userNm", user.getName());
        }
        return "index";
    }

    // 글 저장 화면
    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    // 글 수정 화면
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
