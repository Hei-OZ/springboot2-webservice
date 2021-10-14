package com.springboot.book.jojoldu.web;

import com.springboot.book.jojoldu.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController // JSON 반환 컨트롤러로 만들어 주는 어노테이션. 이전에 @ResponseBody를 각 메소드마다 선언했는데 한 번에 선언 가능
public class HelloController {

    @GetMapping("/hello")   // HTTP Method GET 요청 API로 만들어주는 어노테이션. @RequestMapping(method = RequestMethod.GET) 형식으로 사용하였음
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {   // 외부에서 name으로 넘긴 파라미터를 String name으로 받음. 외부에서 amount로 넘긴 파라미터를 int amount로 받음
        // @RequestParam 어노테이션 => 외부에서 API로 넘긴 파라미터를 받아 옴
        return new HelloResponseDto(name, amount);  // 생성자로 객체 생성
    }
}
