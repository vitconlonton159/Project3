package com.foodstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foodstore.model.User;
import com.foodstore.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Hiển thị danh sách người dùng
    @GetMapping
    public String listUsers(Model model) {
        // Đảm bảo thêm kiểu danh sách người dùng vào model
        model.addAttribute("users", userService.getAllUsers());
        return "adminUser/userList";  // Tên file view sẽ là adminUser/userList
    }

    // Hiển thị form tạo người dùng
    @GetMapping("/create")
    public String createUserForm(Model model) {
        // Tạo một đối tượng User mới và truyền vào form
        model.addAttribute("user", new User());
        return "adminUser/create";  // Tên file view là adminUser/create
    }

    // Xử lý tạo người dùng
    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) {
        userService.createUser(user);  // Lưu người dùng vào database
        return "redirect:/admin/users";  // Quay lại danh sách người dùng
    }

    // Hiển thị form chỉnh sửa người dùng
    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable Long id, Model model) {
        // Lấy thông tin người dùng theo ID
        model.addAttribute("user", userService.getUserById(id).orElse(null));
        return "adminUser/edit";  // Tên file view là adminUser/edit
    }

    // Xử lý cập nhật người dùng
    @PostMapping("/{id}/edit")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        // Đảm bảo userId không bị thay đổi khi cập nhật
        user.setUserId(id);
        userService.updateUser(user);  // Cập nhật người dùng trong database
        return "redirect:/admin/users";  // Quay lại danh sách người dùng
    }

    // Xử lý xóa người dùng
    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);  // Xóa người dùng khỏi database
        return "redirect:/admin/users";  // Quay lại danh sách người dùng
    }
}
