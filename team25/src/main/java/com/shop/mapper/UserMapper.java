package com.shop.mapper;

import com.shop.domain.Role;
import com.shop.domain.User;
import com.shop.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserMapper {
    void save(User user);
    List<User> findAll();
    User findById(Long id);
    User findByUserId(String userId);
    User findByEmail(String email);
    User getLatestUser();
    void update(User user);
    void setUserRole(UserRole userRole);
    UserRole getUserRole(Long userId);
    Role getRole(Integer id);
}
