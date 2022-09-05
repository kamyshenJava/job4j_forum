package ru.job4j.forum.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String body;
    @ManyToOne
    private User author;
    @ManyToOne
    private Post post;
    @Column(columnDefinition = "timestamp without time zone not null default now()")
    private LocalDateTime created;
    @Transient
    private String createdTimeAgo;

    public static Comment of(String body, User author, Post post) {
        Comment comment = new Comment();
        comment.body = body;
        comment.author = author;
        comment.post = post;
        comment.created = LocalDateTime.now();
        return comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCreatedTimeAgo() {
        return createdTimeAgo;
    }

    public void setCreatedTimeAgo(String createdTimeAgo) {
        this.createdTimeAgo = createdTimeAgo;
    }
}
