package com.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Autoincrement 설정, IDENTITY가 많이 사용
    private Long id;

    private String userId;
    private String userName;
    private String pwd;
    private String email;
    private String address;
    private String tel;
    private String regdate;
    private String active;    // JOIN(활동 중) / DORMANT(휴면 중) / WITHDRAW(탈퇴)


    //user에 해당하는 권한이 알아서 조회돼서, roles에 담긴다.
    @ManyToMany
    @JoinTable(
            name = "userRole",
            joinColumns = @JoinColumn(name="userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private List<Role> roles = new ArrayList<>();

}
