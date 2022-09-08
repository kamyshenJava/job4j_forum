package ru.job4j.forum.control;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserControl {
    private final UserService userService;

    public UserControl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signup(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String errorMessage = null;
        if (error != null) {
            errorMessage = "This name is already taken. Please, choose another name.";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "signup";
    }

    @GetMapping("/login")
    public String login(Model model,
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
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user) {
        if (userService.add(user).isEmpty()) {
            return "redirect:/signup?error=true";
        }
        return "redirect:/login";
    }
}
