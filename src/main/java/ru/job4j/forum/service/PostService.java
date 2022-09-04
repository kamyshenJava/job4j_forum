package ru.job4j.forum.service;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.repository.PostMem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostMem postMem;
    private PrettyTime p = new PrettyTime();

    public PostService(PostMem postMem) {
        this.postMem = postMem;
    }

    public List<Post> findAll() {
        return postMem.findAll();
    }

    public Optional<Post> findById(int id) {
        return postMem.findById(id);
    }

    public void save(Post post) {
        postMem.save(post);
    }

    public void update(Post post) {
        postMem.update(post);
    }

    public void saveComment(Comment comment) {
        comment.setCreated(LocalDateTime.now());
        postMem.saveComment(comment);
    }

    public Post setCreatedTimeAgoForComments(Post post) {
        post.getComments().forEach(c -> c.setCreatedTimeAgo(p.format(c.getCreated())));
        return post;
    }
}
