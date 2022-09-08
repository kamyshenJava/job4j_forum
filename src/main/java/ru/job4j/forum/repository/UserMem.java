package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.User;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserMem {
    private final Set<User> users = new HashSet<>();
    private final AtomicInteger ai = new AtomicInteger(1);

    public UserMem() {
        add(User.of("admin", "admin"));
    }

    public Set<User> getUsers() {
        return users;
    }

    public Optional<User> add(User user) {
        user.setId(ai.getAndIncrement());
         if (users.add(user)) {
             return Optional.of(user);
         }
        return Optional.empty();
    }

    public Optional<User> findById(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
    }

    public Optional<User> findUserByNameAndPassword(String name, String password) {
        return users.stream()
                .filter(u -> name.equals(u.getUsername()) && password.equals(u.getPassword()))
                .findFirst();
    }
}
