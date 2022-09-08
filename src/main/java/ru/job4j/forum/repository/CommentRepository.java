package ru.job4j.forum.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.forum.model.Comment;

import java.util.Collection;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    Collection<Comment> findCommentsByPostId(int id);
}
