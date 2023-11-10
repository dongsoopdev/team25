package com.shop.service;

import com.shop.domain.User;

import java.util.List;

public interface UserService {

    public void save(User user);
    public List<User> findAll();
    public User findById(Long id);
    public User findByUserId(Long userId);
    public User getLatestUser();
    public void update(User user);
}
