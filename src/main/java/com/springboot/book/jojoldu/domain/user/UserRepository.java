package com.springboot.book.jojoldu.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);   // email 값을 통해 이미 생성된 사용자인지, 처음 가입하는 사용자인지 판단하기 위한 메소드
    // Optional > null 이 발생할 수 있는 상황에서 NullpointException이 발생하지 않도록 감싸는 Wrapper 클래스로 orElse, orElseGet 등의 함수를 제공한다.
}
