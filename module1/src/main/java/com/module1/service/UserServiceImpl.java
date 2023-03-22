package com.module1.service;

import com.module1.dao.UserRepository;
import com.module1.model.User;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;

@Setter
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(EventService.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserById(long userId) {
        LOGGER.info("Get user with id: " + userId);
        return userRepository.getUserById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with given Id not exist"));
    }

    @Override
    public User getUserByEmail(String email) {
        LOGGER.info("Get user with email: " + email);
        return userRepository.getUserByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User with given email not exist"));
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        LOGGER.info("Get user contains name: " + name);
        return userRepository.getUsersByName(name);
    }

    @Override
    public User createUser(User user) {
        User createdUser = userRepository.createUser(user);
        LOGGER.info("User with id: " + createdUser.getId() + " created");
        return createdUser;
    }

    @Override
    public User updateUser(User user) {
        LOGGER.info("User with id: " + user.getId() + " updated");
        return userRepository.updateUser(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        boolean isDeleted = userRepository.deleteById(userId);
        LOGGER.info("Deleting user with id: " + userId + " ended with successfully - " + isDeleted);
        return isDeleted;
    }
}
