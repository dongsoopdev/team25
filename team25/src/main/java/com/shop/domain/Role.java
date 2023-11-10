package com.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private Long id;
    private String roleName;

    @ManyToMany(mappedBy = "roles") //mappedBy: User클래스에 있는 컬럼명이 된다.
    private List<User> users;

}
