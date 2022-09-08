package ru.job4j.forum.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthorityService authorityService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> add(User user) {
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority(authorityService.findByAuthority("ROLE_USER"));
        try {
            return Optional.of(userRepository.save(user));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
