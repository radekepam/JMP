package com.module1.dao;

import com.module1.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepository {

    private  Storage storage;

    public Optional<User> getUserById(long userId) {
        return Optional.ofNullable(storage.getUsers().get(userId));
    }

    public Optional<User> getUserByEmail(String email) {
        return storage.getUsers().values()
                .stream()
                .filter(e -> e.getEmail().equalsIgnoreCase(email))
                .map(User::new)
                .findAny();
    }

    public List<User> getUsersByName(String name) {
        return storage.getUsers().values()
                .stream()
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()))
                .map(User::new)
                .collect(Collectors.toList());
    }

    public User createUser(User user) {
        long newUserId = generateNewUserId();
        user.setId(newUserId);
        storage.getUsers().put(newUserId, user);
        return new User(user);
    }

    private long generateNewUserId() {
        return storage.getUsers()
                .values()
                .stream()
                .map(User::getId)
                .max(Long::compareTo)
                .orElse(0L) + 1;
    }

    public User updateUser(User user) {
        storage.getUsers().put(user.getId(), user);
        return new User(user);
    }

    public boolean deleteById(long userId) {
        return Optional.ofNullable(storage.getUsers().remove(userId))
                .isPresent();
    }

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
