package com.foodstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.foodstore.model.User;
import com.foodstore.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Đăng ký
    public User registerUser(User user) {
        // Kiểm tra xem email đã tồn tại chưa
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return null; // Email đã tồn tại
        }

        // Nếu không có giá trị role, gán mặc định là "User"
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("User");  // Mặc định gán vai trò là "User"
        }

        // Lưu người dùng vào CSDL
        return userRepository.save(user);
    }

    // Đăng nhập
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user; // Đăng nhập thành công
        }
        return null; // Đăng nhập thất bại
    }





    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        return userRepository.findAll();  // Trả về danh sách tất cả người dùng
    }

    // Tạo người dùng mới
    public void createUser(User user) {
        userRepository.save(user);  // Lưu người dùng vào cơ sở dữ liệu
    }

    // Lấy người dùng theo id
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);  // Tìm người dùng theo id
    }

    // Cập nhật người dùng
    public void updateUser(User user) {
        userRepository.save(user);  // Cập nhật thông tin người dùng
    }

    // Xóa người dùng
    public void deleteUser(Long id) {
        userRepository.deleteById(id);  // Xóa người dùng
    }
}
