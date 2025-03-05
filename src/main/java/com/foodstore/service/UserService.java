package com.foodstore.service;

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
        // Kiểm tra email đã tồn tại
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return null; // Hoặc ném exception
        }
        // Chỗ này nên mã hóa mật khẩu, demo thì để nguyên
        return userRepository.save(user);
    }

    // Đăng nhập
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user; // Đăng nhập thành công
        }
        return null; // Sai email hoặc password
    }
}
