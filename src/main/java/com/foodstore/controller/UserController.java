package com.foodstore.controller;

import com.foodstore.model.User;
import com.foodstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Hiển thị danh sách người dùng
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<User> users = userRepository.findAll(); // Lấy tất cả người dùng từ CSDL
        model.addAttribute("users", users); // Truyền danh sách người dùng cho view
        return "users"; // Trả về trang users.html
    }

    // Hiển thị form thêm người dùng
    @GetMapping("/add")
    public String showAddForm() {
        return "add_user"; // Trả về trang add_user.html
    }

    // Thêm người dùng mới
    @PostMapping("/add")
    public String addUser(@RequestParam String fullName, 
                          @RequestParam String email, 
                          @RequestParam String password, 
                          @RequestParam String phoneNumber) {
        User user = new User(fullName, email, password, phoneNumber);
        userRepository.save(user); // Lưu người dùng vào cơ sở dữ liệu
        return "redirect:/admin/user/dashboard"; // Chuyển hướng về dashboard
    }

    // Sửa thông tin người dùng
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));
        model.addAttribute("user", user);
        return "edit_user"; // Trả về trang edit_user.html
    }

    // Sửa thông tin người dùng
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, 
                             @RequestParam String fullName, 
                             @RequestParam String email, 
                             @RequestParam String password, 
                             @RequestParam String phoneNumber) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user); // Cập nhật người dùng vào cơ sở dữ liệu
        return "redirect:/admin/user/dashboard"; // Chuyển hướng về dashboard
    }

    // Xóa người dùng
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id); // Xóa người dùng khỏi cơ sở dữ liệu
        return "redirect:/admin/user/dashboard"; // Chuyển hướng về dashboard
    }
}
