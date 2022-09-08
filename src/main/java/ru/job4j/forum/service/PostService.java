package ru.job4j.forum.service;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.repository.CommentRepository;
import ru.job4j.forum.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PrettyTime p = new PrettyTime();

    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public List<Post> findAll() {
        return (List<Post>) postRepository.findAll();
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public void update(Post post) {
        List<Comment> comments = new ArrayList<>(commentRepository.findCommentsByPostId(post.getId()));
        post.setComments(comments);
        postRepository.save(post);
    }

    public void saveComment(Comment comment) {
        comment.setCreated(LocalDateTime.now());
        commentRepository.save(comment);
    }

    public Post setCreatedTimeAgoForComments(Post post) {
        post.getComments().forEach(c -> c.setCreatedTimeAgo(p.format(c.getCreated())));
        return post;
    }
}
