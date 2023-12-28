package com.example.UserManagement.service;

import com.example.UserManagement.model.User;

import java.util.List;

public interface IUserService {
    User addUser(User user);
    List<User> getUsers();
    User updateUser(User user, Long id);
    User getUserById(Long id);
    void deleteUser(Long id);


}
