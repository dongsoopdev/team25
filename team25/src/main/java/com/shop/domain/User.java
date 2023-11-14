package com.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id; // 개인 번호 -> 전 userId
    private String userId; // 로그인 아이디 -> 전 loginId
    private String userName; // 유저 이름
    private String password;
    private String email;
    private String address;
    private String tel;
    private String regdate;
    private int point;
    private String active;    // JOIN(활동 중) / DORMANT(휴면 중) / WITHDRAW(탈퇴)

}
