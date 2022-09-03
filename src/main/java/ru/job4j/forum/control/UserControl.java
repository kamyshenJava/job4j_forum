package ru.job4j.forum.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserControl {
    private final UserService userService;

    public UserControl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signup(@RequestParam(value = "error", required = false) String error,
                         Model model, HttpSession session) {
        addUserToModel(model, session);
        String errorMessage = null;
        if (error != null) {
            errorMessage = "This name is already taken. Please, choose another name.";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "signup";
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session,
                        @RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = "Please, enter correct name and password.";
        }
        if (logout != null) {
            errorMessage = "You have been successfully logged out";
        }
        model.addAttribute("errorMessage", errorMessage);
        addUserToModel(model, session);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/signup?error=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", regUser.get());
        return "redirect:/index";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> regUser = userService.findUserByNameAndPassword(user.getName(), user.getPassword());
        if (regUser.isEmpty()) {
            return "redirect:/login?error=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", regUser.get());
        return "redirect:/index";
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
