package com.shop.service;

import com.shop.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public void save(User user) {

    }

    @Override
    public List<User> findAll() {
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
    public User getLatestUser() {
        return null;
    }

    @Override
    public void update(User user) {

    }
}
