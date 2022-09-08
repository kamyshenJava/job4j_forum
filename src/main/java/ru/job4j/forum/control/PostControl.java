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
import ru.job4j.forum.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class PostControl {
    private final PostService postService;
    private final UserService userService;

    public PostControl(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String create(Model model, Principal principal) {
        String name = principal.getName();
        if ("anonymousUser".equals(name)) {
            return "redirect:/index";
        }
        model.addAttribute("user", name);
        return "post/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Post post, Principal principal) {
        String name = principal.getName();
        User user = userService.findByUsername(name).get();
        post.setAuthor(user);
        post.setCreated(LocalDateTime.now());
        postService.save(post);
        return "redirect:/index";
    }

    @GetMapping("/edit")
    public String edit(Model model, Principal principal, @RequestParam("id") int id) {
        String name = principal.getName();
        if ("anonymousUser".equals(name)) {
            return "redirect:/index";
        }
        Optional<Post> post = postService.findById(id);
        if (post.isEmpty() || !post.get().getAuthor().getUsername().equals(name)) {
            return "redirect:/index";
        }
        model.addAttribute("user", name);
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
    public String post(Model model, Principal principal, @RequestParam("id") int id) {
        String name = principal.getName();
        if ("anonymousUser".equals(name)) {
            return "redirect:/index";
        }
        Optional<Post> post = postService.findById(id);
        if (post.isEmpty()) {
            return "redirect:/index";
        }
        model.addAttribute("user", name);
        model.addAttribute("post", postService.setCreatedTimeAgoForComments(post.get()));
        return "post/post";
    }

    @PostMapping("/comment")
    public String saveComment(@ModelAttribute Comment comment, HttpServletRequest req, Principal principal) {
        int postId = Integer.parseInt(req.getParameter("post_id"));
        Post post = postService.findById(postId).get();
        String name = principal.getName();
        User user = userService.findByUsername(name).get();
        comment.setPost(post);
        comment.setAuthor(user);
        postService.saveComment(comment);
        return String.format("redirect:/post?id=%d", postId);
    }

}
