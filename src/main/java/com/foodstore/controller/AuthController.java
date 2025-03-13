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
        return "redirect:/login";
    }
    // Trang hiển thị form đăng nhập (GET)
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login"; // Tên file login.html
    }

    // Xử lý khi người dùng submit form đăng nhập (POST)
    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        User user = userService.login(email, password);
        if (user == null) {
            model.addAttribute("error", "Email hoặc mật khẩu sai!");
            return "login"; // Quay lại trang login và hiển thị lỗi
        }

        // Kiểm tra vai trò của người dùng
        if ("ADMIN".equals(user.getRole())) {
            // Nếu là admin, chuyển đến trang dashboard
            return "redirect:/admin/dashboard";
        } else {
            // Nếu là người dùng bình thường, chuyển đến trang home
            return "redirect:/home";
        }
    }
}

