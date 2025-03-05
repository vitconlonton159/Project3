package com.foodstore.controller;

import com.foodstore.model.User;
import com.foodstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Trang hiển thị form đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Tên file register.html
    }

    // Xử lý khi người dùng submit form đăng ký
    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user, Model model) {
        User registeredUser = userService.registerUser(user);
        if (registeredUser == null) {
            model.addAttribute("error", "Email đã tồn tại!");
            return "register";
        }
        // Đăng ký thành công, chuyển đến trang đăng nhập
        return "redirect:/login";
    }

    // Trang hiển thị form đăng nhập
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login"; // Tên file login.html
    }

    // Xử lý khi người dùng submit form đăng nhập
    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        User user = userService.login(email, password);
        if (user == null) {
            model.addAttribute("error", "Email hoặc mật khẩu sai!");
            return "login";
        }
        // Đăng nhập thành công, chuyển hướng về home
        return "redirect:/home";
    }
}
