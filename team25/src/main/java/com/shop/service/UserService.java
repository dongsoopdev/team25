package com.shop.service;

import com.shop.domain.User;
import com.shop.domain.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface UserService {

    public User save(User user);
    public List<User> findAll();

    public User getUserById(Long id);
    public User getByEmail(String email);
    public User getByName(String name);
    public User findById(Long id);
    public User findByUserId(Long userId);
    public User findByPw(String email, String tel, String name);
    public User getLatestUser();

    public int userJoin(User user);
    public int updateUser(User user);
    public int updateLevel(String name, String lev);
    public int removeUser(String name);

    public int updatePasswordNoChange(User user);
    public int getWithdraw(Long id);
    public int getActivate(String name);
    public int getDormant(String name);
    public PasswordEncoder passwordEncoder();
    UserRole getUserRole(Long id);
}
