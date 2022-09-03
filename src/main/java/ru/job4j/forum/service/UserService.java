package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.UserMem;

import java.util.Optional;

@Service
public class UserService {

    private UserMem userMem;

    public UserService(UserMem userMem) {
        this.userMem = userMem;
    }

    public Optional<User> findById(int id) {
        return userMem.findById(id);
    }

    public Optional<User> add(User user) {
        return userMem.add(user);
    }

    public Optional<User> findUserByNameAndPassword(String name, String password) {
        return userMem.findUserByNameAndPassword(name, password);
    }
}
