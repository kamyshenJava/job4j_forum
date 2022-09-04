package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostMem {
    private Map<Integer, Post> posts = new HashMap<>();
    private AtomicInteger ai = new AtomicInteger(1);

    public Map<Integer, Post> getPosts() {
        return posts;
    }

    public void setPosts(Map<Integer, Post> posts) {
        this.posts = posts;
    }

    public List<Post> findAll() {
        return posts.values().stream().toList();
    }

    public Optional<Post> findById(int id) {
        return posts.values()
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    public void save(Post post) {
        int id = ai.getAndIncrement();
        post.setId(id);
        posts.put(id, post);
    }

    public void update(Post post) {
        posts.values()
            .stream()
            .filter(p -> p.getId() == post.getId())
            .limit(1)
            .forEach(p -> {
                p.setName(post.getName());
                p.setDescription(post.getDescription());
            });
    }

    public void saveComment(Comment comment) {
        posts.values()
                .stream()
                .filter(p -> p.getId() == comment.getPost().getId())
                .limit(1)
                .forEach(p -> p.getComments().add(comment));
    }
}
