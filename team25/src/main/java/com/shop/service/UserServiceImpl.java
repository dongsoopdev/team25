package com.shop.service;

import com.shop.domain.Role;
import com.shop.domain.User;
import com.shop.domain.UserRole;
import com.shop.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public void save(User user, Integer roleId) {
        user.setPwd(passwordEncoder.encode(user.getPwd())); //비밀번호 암호화
        userMapper.save(user);  //회원 가입
        Role searchRole = userMapper.getRole(roleId);   //현재 가입한 회원의 등급 ID
        User searchUser = userMapper.getLatestUser();   //현재 가입한 회원의 ID
        UserRole userRole = new UserRole(searchUser.getId(), searchRole.getId());
        userMapper.setUserRole(userRole);   //UserRole 테이블에 정보 저장
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public User findByUserId(String username) {
        return userMapper.findByUserId(username);
    }

    @Override
    public User findByEmail(String email){
        return userMapper.findByEmail(email);
    }

    @Override
    public User getLatestUser() {
        return userMapper.getLatestUser();
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public User loginPro(User user) {
        User userData = userMapper.findByUserId(user.getUserId());
        if(passwordEncoder.matches(user.getPwd(), userData.getPwd())){
            return userData;
        } else {
            return null;
        }
    }
    @Override
    public UserRole getUserRole(Long userId){
        UserRole userRole = userMapper.getUserRole(userId);
        return userRole;
    }
    @Override
    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }
}