package ru.job4j.forum.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.UserRepository;
import ru.job4j.forum.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> add(User user) {
        try {
            return Optional.of(userRepository.save(user));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findUserByNameAndPassword(String name, String password) {
        return Optional.ofNullable(userRepository.findByNameAndPassword(name, password));
    }
}
