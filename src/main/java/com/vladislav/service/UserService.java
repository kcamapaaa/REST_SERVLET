package com.vladislav.service;

import com.vladislav.model.User;
import com.vladislav.repository.UserRepository;
import com.vladislav.repository.hibernate.HiberUserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository = new HiberUserRepository();

    public User getUserById(int id) {
        return userRepository.getById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public User addNewUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.update(user);
    }

    public boolean deleteUserById(int id) {
        return userRepository.deleteById(id);
    }
}
