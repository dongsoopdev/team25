package com.shop.service;

import com.shop.domain.User;
import com.shop.domain.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface UserService {

    //회원 가입
    void save(User user, Integer roleId);

    //회원 전체 조회
    List<User> findAll();

    //회원 고유 번호 찾기
    User findById(Long id);

    //회원 아이디 찾기
    User findByUserId(String userId);

    //회원 이메일 찾기
    User findByEmail(String email);

    //최근 가입 회원 찾기
    User getLatestUser();

    //회원 정보 수정
    void update(User user);

    //로그인 처리
    public User loginPro(User user);

    //회원 권한 가져오기
    UserRole getUserRole(Long id);

    //비밀번호 암호화
    public PasswordEncoder passwordEncoder();
}
