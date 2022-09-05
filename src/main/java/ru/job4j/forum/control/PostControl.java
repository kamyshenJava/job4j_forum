package ru.job4j.forum.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Controller
public class PostControl {
    private PostService postService;

    public PostControl(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/create")
    public String create(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/index";
        }
        addUserToModel(model, session);
        return "post/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Post post, HttpSession session) {
        User user = (User) session.getAttribute("user");
        post.setAuthor(user);
        post.setCreated(LocalDateTime.now());
        postService.save(post);
        return "redirect:/index";
    }

    @GetMapping("/edit")
    public String edit(Model model, HttpSession session, @RequestParam("id") int id) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/index";
        }
        Optional<Post> post = postService.findById(id);
        if (post.isEmpty() || !post.get().getAuthor().equals(user)) {
            return "redirect:/";
        }
        addUserToModel(model, session);
        model.addAttribute("post", post.get());
        return "post/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Post post, HttpServletRequest req) {
        post.setCreated(LocalDateTime.parse(req.getParameter("created1")));
        postService.update(post);
        return "redirect:/index";
    }

    @GetMapping("/post")
    public String post(Model model, HttpSession session, @RequestParam("id") int id) {
        if (session.getAttribute("user") == null) {
            return "redirect:/index";
        }
        Optional<Post> post = postService.findById(id);
        if (post.isEmpty()) {
            return "redirect:/";
        }
        addUserToModel(model, session);
        model.addAttribute("post", postService.setCreatedTimeAgoForComments(post.get()));
        return "post/post";
    }

    @PostMapping("/comment")
    public String saveComment(@ModelAttribute Comment comment, HttpServletRequest req, HttpSession session) {
        int postId = Integer.parseInt(req.getParameter("post_id"));
        Post post = postService.findById(postId).get();
        User user = (User) session.getAttribute("user");
        comment.setPost(post);
        comment.setAuthor(user);
        postService.saveComment(comment);
        return String.format("redirect:/post?id=%d", postId);
    }

    private void addUserToModel(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("guest");
        }
        model.addAttribute("user", user);
    }

}
