package com.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String userId;
    private String userName;
    private String pwd;
    private String email;
    private String address;
    private String tel;
    private String regdate;
    private String active;    // JOIN(활동 중) / DORMANT(휴면 중) / WITHDRAW(탈퇴)

}
