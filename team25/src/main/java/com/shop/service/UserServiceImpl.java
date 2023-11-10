package com.shop.service;

import com.shop.domain.Role;
import com.shop.domain.User;
import com.shop.domain.UserRole;
import com.shop.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;


    @Override
    public User save(User user) {
        // 1.비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(user.getPwd());
        user.setPwd(encodedPassword);

        // 2.회원 활성화 여부 - 기본적으로 enabled로 설정
        //user.setEnabled(true);

        // 3. role정보 추가
        Role role = new Role();
        role.setId(1L);
        user.getRoles().add(role);
        return userMapper.save(user);
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public User getByName(String name) {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User findByUserId(Long userId) {
        return null;
    }

    @Override
    public User findByPw(String email, String tel, String name) {
        return null;
    }

    @Override
    public User getLatestUser() {
        return null;
    }

    @Override
    public int userJoin(User user) {
        return 0;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public int updateLevel(String name, String lev) {
        return 0;
    }

    @Override
    public int removeUser(String name) {
        return 0;
    }

    @Override
    public int updatePasswordNoChange(User user) {
        return 0;
    }

    @Override
    public int getWithdraw(Long id) {
        return 0;
    }

    @Override
    public int getActivate(String name) {
        return 0;
    }

    @Override
    public int getDormant(String name) {
        return 0;
    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return null;
    }

    @Override
    public UserRole getUserRole(Long id) { return userMapper.getUserRole(id); }
}
