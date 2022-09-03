package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostMem {
    private List<Post> posts = new ArrayList<>();
    private AtomicInteger ai = new AtomicInteger(1);

    public List<Post> getPost() {
        return posts;
    }

    public void setPost(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> findAll() {
        return posts;
    }

    public Optional<Post> findById(int id) {
        return posts.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    public void save(Post post) {
        post.setId(ai.getAndIncrement());
        posts.add(post);
    }

    public void update(Post post) {
        posts.stream()
                .filter(p -> p.getId() == post.getId())
                .limit(1)
                .forEach(p -> {
                    p.setName(post.getName());
                    p.setDescription(post.getDescription());
                });
    }
}
