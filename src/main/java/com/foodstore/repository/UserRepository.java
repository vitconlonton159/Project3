package com.foodstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.foodstore.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Thêm phương thức tùy chỉnh nếu cần
    User findByEmail(String email);
}
