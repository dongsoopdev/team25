package com.shop.mapper;

import com.shop.domain.User;
import com.shop.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper
public interface UserMapper {
    User save(User user);
    List<User> findAll();

    User getUserById(Long id);
    User getByEmail(String email);
    User getByName(String name);
    User findById(Long id);
    User findByUserId(Long userId);
    User findByPw(String email, String tel, String name);
    User getLatestUser();

    int userJoin(User user);
    int updateUser(User user);
    int updateLevel(String name, String lev);
    int removeUser(String name);

    int updatePasswordNoChange(User user);
    int getWithdraw(Long id);
    int getActivate(String name);
    int getDormant(String name);
    PasswordEncoder passwordEncoder();
    UserRole getUserRole(Long id);
}
