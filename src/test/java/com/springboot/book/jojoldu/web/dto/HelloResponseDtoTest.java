package com.springboot.book.jojoldu.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        // given
        String name = "test";
        int amount = 1000;

        // when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // then
        // assertThat => assertj(테스트 검증 라이브러리)의 검증 메소드. 검증하고싶은 대상을 메소드 인자로 받음. 메소드 체이닝 지원하기에 isEqualTo 이어서 사용
        // isEqualTo => assertj의 동등 비교 메소드. assertThat의 값과 isEqualTo의 값을 비교하여 같을때만 성공
        // JUnit에도 assertThat이 있으나 assertj가 사용이 편하여 이렇게 코딩하였다고 함
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}